package com.abschlussProjekt.dotastats.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abschlussProjekt.dotastats.data.database.DotaStatsDatabase
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageEXP
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageGold
import com.abschlussProjekt.dotastats.data.datamodels.api.ProMatchAPI
import com.abschlussProjekt.dotastats.data.datamodels.api.ProMatchDetailAPI

private const val TAG = "Repository"

class Repository(
    private val apiService: OpenDotaApiService,
    private val database: DotaStatsDatabase
) {

    private val _recentProMatches = MutableLiveData<List<ProMatchAPI>>()
    val recentProMatches: LiveData<List<ProMatchAPI>>
        get() = _recentProMatches

    private val _detailProMatch = MutableLiveData<ProMatchDetailAPI>()
    val detailProMatch: LiveData<ProMatchDetailAPI>
        get() = _detailProMatch


    // Hole neuste Pro Matches
    suspend fun getRecentProMatches() {
        try {
            _recentProMatches.postValue(apiService.getRecentProMatches())
        } catch (ex: Exception) {
            Log.e("$TAG-getRecentProMatches", "Error loading data from api: $ex")
        }
    }

    // Hole MatchDetails per ID
    suspend fun getMatchById(id: Long) {
        try {
            val matchDetailAPI = apiService.getMatchById(id)
            Log.e("DB Match", matchDetailAPI.toProMatchDetailDB().toString())
            Log.e("Gold Vorteil", matchDetailAPI.radiant_gold_adv.toString())
            _detailProMatch.postValue(matchDetailAPI)

            // MatchDetailAPI als MatchDetailDB in DB abspeichern
            database.dotaStatsDao.insertProMatchDetailDB(matchDetailAPI.toProMatchDetailDB())
            Log.e("DB Match", matchDetailAPI.toProMatchDetailDB().toString())

            // Goldvorteil in DB speichern
            matchDetailAPI.radiant_gold_adv?.forEachIndexed { minute, gold ->
                database.dotaStatsDao.insertAdvantageGold(
                    AdvantageGold(
                        matchDetailAPI.match_id,
                        minute,
                        gold
                    )
                )
            }
            // EXPvorteil in DB speichern
            matchDetailAPI.radiant_xp_adv?.forEachIndexed { minute, exp ->
                database.dotaStatsDao.insertAdvantageEXP(
                    AdvantageEXP(
                        matchDetailAPI.match_id,
                        minute,
                        exp
                    )
                )
            }


            matchDetailAPI.players.forEach {
                database.dotaStatsDao.insertPlayer(it.toPlayerDB())
            }

            Log.e("MATCH", database.dotaStatsDao.getMatchByID(matchDetailAPI.match_id).toString())
            Log.e(
                "Radiant",
                database.dotaStatsDao.getMatchByID(matchDetailAPI.match_id).players.filter { it.isRadiant }
                    .sortedBy { it.player_slot }.toString()
            )
            Log.e(
                "Dire",
                database.dotaStatsDao.getMatchByID(matchDetailAPI.match_id).players.filter { !it.isRadiant }
                    .sortedBy { it.player_slot }.toString()
            )
        } catch (ex: Exception) {
            Log.e("$TAG-getMatchById", "Error loading data from api: $ex")
        }
    }


    suspend fun initDB() {
        // Wenn Datenbank keine Einträge hat, initialisiere sie mit Daten
        if (database.dotaStatsDao.getHeroCount() == 0) {

            //Hole Heldeninformation von der API
            val heroes = apiService.getHeroes()
            //wandle die HeroMap in eine Liste und speichere sie in die DB
            database.dotaStatsDao.insertHeroList(heroes.values.toList())

            val items = apiService.getItems()
            database.dotaStatsDao.insertItemList(items.values.toList())

            // Hole alle IDS von den Abilities
            val abilityIDS = apiService.getAbilityIDS()
            // Tausche Key -> Value und Value -> Key, um besser suchen zu können
            val iDMap = abilityIDS.map { (k, v) ->
                v to k
            }.toMap()

            // Hole alle Abilities
            var abilities = apiService.getAbilities()

            // Lösche Abilities, deren Name & Image nicht vorhanden sind
            abilities = abilities.filter {
                it.value.name?.isNotBlank() == true &&
                        it.value.img != null
            }

            // Iteriere durch die Map und füge die ID's den Abilities hinzu
            abilities.forEach {
                it.value.id = iDMap[it.key]?.toLong()
            }

            // wandle Map zur Liste und speichere sie in die DB
            database.dotaStatsDao.insertAbilityList(abilities.values.toList())
        }
    }
}