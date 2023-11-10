package com.abschlussProjekt.dotastats.data.datamodels

import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item

data class Player(
    val personaname: String?,
    val name: String?,
    val match_id: Long,
    val account_id: Long,
    val isRadiant: Boolean,
    val player_slot: Int,
    val ability_upgrades_arr: List<Ability>,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val denies: Int,
    val gold_per_min: Int,
    val xp_per_min: Int,
    val hero_damage: Int,
    val hero_healing: Int,
    val hero: Hero,
    val level: Int,


    val inventory: List<Item?>,
    val item_neutral: Item?,
    val backpack: List<Item?>,

    val last_hits: Int,
    val net_worth: Int,

    val tower_damage: Int,

    val gold_t: List<Int>?,
    val xp_t: List<Int>?
)