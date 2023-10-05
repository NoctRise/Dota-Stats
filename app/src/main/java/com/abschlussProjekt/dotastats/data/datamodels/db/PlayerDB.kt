package com.abschlussProjekt.dotastats.data.datamodels.db

import androidx.room.Entity

@Entity(
    tableName = "Player",
    primaryKeys = ["match_id", "account_id"]
)
data class PlayerDB(
    val personaname: String?,
    val name: String?,
    val match_id: Long,
    val account_id: Long,
    val isRadiant: Boolean,
    val player_slot : Int,

    //val ability_upgrades_arr: List<Int>?,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val denies: Int,
    val gold_per_min: Int,
    val xp_per_min: Int,
    val hero_damage: Int,
    val hero_healing: Int,

    val hero_id: Long,
    val level: Int,

    val item_0: Int?,
    val item_1: Int?,
    val item_2: Int?,
    val item_3: Int?,
    val item_4: Int?,
    val item_5: Int?,
    val item_neutral: Int?,
    val backpack0: Int?,
    val backpack1: Int?,
    val backpack2: Int?,

    val last_hits: Int,
    val net_worth: Int,
//    val permanent_buffs: List<PermanentBuff>,

    //val purchase_log: List<ItemPurchase>?,
    val tower_damage: Int
)