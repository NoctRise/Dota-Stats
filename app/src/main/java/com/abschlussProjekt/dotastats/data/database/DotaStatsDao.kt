package com.abschlussProjekt.dotastats.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item

@Dao
interface DotaStatsDao {

    @Upsert
    fun insertHeroList(heroList: List<Hero>)

    @Upsert
    fun insertItemList(itemList: List<Item>)

    @Upsert
    fun insertAbilityList(abilityList: List<Ability>)

    @Query("SELECT COUNT(*) FROM Hero")
    fun getHeroCount(): Int

    @Query("SELECT * FROM Hero WHERE id=:id")
    fun getHeroByID(id: Long): Hero

    @Query("SELECT * FROM Ability WHERE id=:id")
    fun getAbilityByID(id: Long): Ability

    @Query("SELECT * FROM Item WHERE id=:id")
    fun getItemByID(id: Long): Item
}