package com.abschlussProjekt.dotastats.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abschlussProjekt.dotastats.data.database.DotaStatsDatabase
import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetailAPI

private const val TAG = "Repository"

class Repository(
    private val apiService: OpenDotaApiService,
    private val database: DotaStatsDatabase
) {

    private val _recentProMatches = MutableLiveData<List<ProMatch>>()
    val recentProMatches: LiveData<List<ProMatch>>
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
            val matchDetail = apiService.getMatchById(id)
            _detailProMatch.postValue(matchDetail)

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