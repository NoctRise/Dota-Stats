package com.abschlussProjekt.dotastats.data.datamodels

import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item
import com.squareup.moshi.Json

data class PlayerAPI(
    val personaname: String?,
    val name: String?,
    val account_id: Long?,
    val isRadiant: Boolean,
    val player_slot: Int,
    val ability_upgrades_arr: List<Long>?,
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

    val item_0: Long?,
    val item_1: Long?,
    val item_2: Long?,
    val item_3: Long?,
    val item_4: Long?,
    val item_5: Long?,
    val item_neutral: Long?,
    @Json(name = "backpack_0")
    val backpack0: Long?,
    @Json(name = "backpack_1")
    val backpack1: Long?,
    @Json(name = "backpack_2")
    val backpack2: Long?,

    val last_hits: Int,
    val net_worth: Int,

    val tower_damage: Int,
    val gold_t: List<Int>?,
    val xp_t: List<Int>?

) {
    fun toPLayer(
        abilityList: List<Ability>,
        hero: Hero,
        inventory: List<Item?>,
        backpack: List<Item?>,
        neutral: Item?
    ) = Player(
        personaname,
        name,
        account_id,
        isRadiant,
        player_slot,
        abilityList,
        kills,
        deaths,
        assists,
        denies,
        gold_per_min,
        xp_per_min,
        hero_damage,
        hero_healing,
        hero,
        level,
        inventory,
        neutral,
        backpack,
        last_hits,
        net_worth,
        tower_damage,
        gold_t,
        xp_t
    )

}
