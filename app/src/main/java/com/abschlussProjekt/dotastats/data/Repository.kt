package com.abschlussProjekt.dotastats.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abschlussProjekt.dotastats.data.database.DotaStatsDatabase
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.data.datamodels.PlayerProfile
import com.abschlussProjekt.dotastats.data.datamodels.PlayerRecentMatch
import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetail
import com.abschlussProjekt.dotastats.data.datamodels.ProTeam
import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item

private const val TAG = "Repository"

class Repository(
    private val apiService: OpenDotaApiService, private val database: DotaStatsDatabase
) {

    private val _recentProMatches = MutableLiveData<List<ProMatch>>()
    val recentProMatches: LiveData<List<ProMatch>>
        get() = _recentProMatches

    private val _detailProMatch = MutableLiveData<ProMatchDetail?>()
    val detailProMatch: LiveData<ProMatchDetail?>
        get() = _detailProMatch

    private val _playerProfile = MutableLiveData<PlayerProfile>()
    val playerProfile: LiveData<PlayerProfile>
        get() = _playerProfile

    private val _playerWinLose = MutableLiveData<Map<String, Int>?>()
    val playerWinLose: LiveData<Map<String, Int>?>
        get() = _playerWinLose

    private val _proTeams = MutableLiveData<List<ProTeam>>()
    val proTeams: LiveData<List<ProTeam>>
        get() = _proTeams

    private val _playerRecentMatches = MutableLiveData<List<PlayerRecentMatch>>()
    val playerRecentMatches: LiveData<List<PlayerRecentMatch>>
        get() = _playerRecentMatches

    suspend fun getTeams() {
        try {
            _proTeams.postValue(apiService.getTeams())
        } catch (ex: Exception) {
            Log.e("$TAG-getTeams", "Error loading data from api: $ex")
        }
    }


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
            _detailProMatch.postValue(null)
            val matchDetailAPI = apiService.getMatchById(id)

            // Iteriere durch die Playerliste und füge die Heroes und die Abilities hinzu
            val playerList = mutableListOf<Player>()

            matchDetailAPI.players.forEach { player ->
                val hero = database.dotaStatsDao.getHeroByID(player.hero_id)
                val abilities = mutableMapOf<Long, Ability>()
                val playerAbilities = mutableListOf<Ability>()


                player.ability_upgrades_arr.forEach { abilityID ->
                    if (!abilities.containsKey(abilityID)) {
                        val ability = database.dotaStatsDao.getAbilityByID(abilityID)
                        playerAbilities.add(ability)
                        abilities[abilityID] = ability
                    } else abilities[abilityID]?.let { ability -> playerAbilities.add(ability) }
                }


                val inventory = getPlayerItems(
                    player.item_0,
                    player.item_1,
                    player.item_2,
                    player.item_3,
                    player.item_4,
                    player.item_5
                )

                val backpack = getPlayerItems(player.backpack0, player.backpack1, player.backpack2)
                val neutralItem = getPlayerItems(player.item_neutral).first()

                playerList.add(
                    player.toPLayer(
                        playerAbilities, hero, inventory, backpack, neutralItem
                    )
                )
            }


            _detailProMatch.postValue(matchDetailAPI.toProMatchDetail(playerList))
            Log.e("Match", matchDetailAPI.toProMatchDetail(playerList).toString())

        } catch (ex: Exception) {
            Log.e("$TAG-getMatchById", "Error loading data from api: $ex")
        }
    }


    private val itemList = mutableMapOf<Long, Item>()
    private fun getPlayerItems(vararg items: Long?): List<Item?> {
        val itemList = mutableListOf<Item?>()

        items.forEach { item ->
            item?.let {
                itemList.add(getItemByID(item))
            }
        }
        return itemList
    }


    suspend fun getPlayerProfileByID(accountID: Long) {
        try {
            _playerProfile.postValue(apiService.getPlayerProfileByID(accountID).profile)
        } catch (ex: Exception) {
            Log.e("$TAG-getPlayerRecentMatchesByID", "Error loading data from api: $ex")
        }
    }

    suspend fun getPlayerWinLoseByID(accountID: Long) {
        try {
            _playerWinLose.postValue(null)
            _playerWinLose.postValue(apiService.getPlayerWinLoseByID(accountID))
        } catch (ex: Exception) {
            Log.e("$TAG-getPlayerRecentMatchesByID", "Error loading data from api: $ex")
        }
    }

    suspend fun getPlayerRecentMatchesByID(accountID: Long) {
        try {
            val matches = apiService.getPlayerRecentMatchesByID(accountID)
            matches.forEach { it.hero = database.dotaStatsDao.getHeroByID(it.hero_id) }
            _playerRecentMatches.postValue(matches)
        } catch (ex: Exception) {
            Log.e("$TAG-getPlayerRecentMatchesByID", "Error loading data from api: $ex")
        }
    }


    // Suche nach Item per itemID
    private fun getItemByID(itemID: Long): Item =
        if (!itemList.containsKey(itemID)) database.dotaStatsDao.getItemByID(itemID)
        else itemList[itemID]!!

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
            // Tausche Key -> Value mit Value -> Key, um besser suchen zu können
            val iDMap = abilityIDS.map { (k, v) ->
                v to k
            }.toMap()

            // Hole alle Abilities
            val abilities = apiService.getAbilities()

            // Iteriere durch die Map und füge die ID's den Abilities hinzu
            abilities.forEach {
                it.value.id = iDMap[it.key]?.toLong()
            }

            // wandle Map zur Liste und speichere sie in die DB
            database.dotaStatsDao.insertAbilityList(abilities.values.toList())
        }
    }
}