package com.abschlussProjekt.dotastats.data.datamodels

import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero

data class PlayerRecentMatch (
    val match_id: Long,
    val duration: Int,
    val start_time: Long,
    val radiant_win: Boolean?,
    val hero_id : Long,
    var hero : Hero?,
    val kills : Int,
    val deaths : Int,
    val assists : Int)