package com.abschlussProjekt.dotastats.data.datamodels.api

import com.abschlussProjekt.dotastats.data.datamodels.db.PlayerDB

data class PlayerAPI(
    val personaname: String?,
    val name: String?,
    val match_id: Long,
    val account_id: Long,
    val isRadiant: Boolean,
    val player_slot: Int,
    val ability_upgrades_arr: List<Int>,
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

    val purchase_log: List<ItemPurchase>?,
    val tower_damage: Int
) {
    fun toPlayerDB(): PlayerDB =
        PlayerDB(
            personaname,
            name,
            match_id,
            account_id,
            isRadiant,
            player_slot,
            kills,
            deaths,
            assists,
            denies,
            gold_per_min,
            xp_per_min,
            hero_damage,
            hero_healing,
            hero_id,
            level,
            item_0,
            item_1,
            item_2,
            item_3,
            item_4,
            item_5,
            item_neutral,
            backpack0,
            backpack1,
            backpack2,
            last_hits,
            net_worth,
            tower_damage
        )
}