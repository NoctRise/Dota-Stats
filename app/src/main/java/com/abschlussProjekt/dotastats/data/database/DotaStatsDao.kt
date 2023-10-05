package com.abschlussProjekt.dotastats.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchWithDetails
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageEXP
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageGold
import com.abschlussProjekt.dotastats.data.datamodels.db.PlayerDB
import com.abschlussProjekt.dotastats.data.datamodels.db.ProMatchDetailDB
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Item

@Dao
interface DotaStatsDao {


    @Query("SELECT * FROM Match_details WHERE match_id= :matchID")
    fun getMatchByID(matchID: Long): ProMatchWithDetails

    @Upsert
    fun insertProMatchDetailDB(matchDB: ProMatchDetailDB)


    @Upsert
    fun insertAdvantageGold(advantageGold: AdvantageGold)

    @Upsert
    fun insertAdvantageEXP(exp: AdvantageEXP)

    @Upsert
    fun insertPlayer(playerDB: PlayerDB)

    @Upsert
    fun insertHeroList(heroList: List<Hero>)

    @Upsert
    fun insertItemList(itemList: List<Item>)

    @Upsert
    fun insertAbilityList(abilityList: List<Ability>)

    @Query("SELECT COUNT(*) FROM Hero")
    fun getHeroCount(): Int
}