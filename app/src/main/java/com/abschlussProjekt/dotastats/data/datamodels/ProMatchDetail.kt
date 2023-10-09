package com.abschlussProjekt.dotastats.data.datamodels

data class ProMatchDetail(
    val match_id: Long,
    val duration: Int,
    //val game_mode: Int,
    //val leagueid: Long,
    //val pick_bans,

    val radiant_score: Int,
    val radiant_team_id: Long?,
    val dire_score: Int,
    val dire_team_id: Long?,
    val radiant_win: Boolean,
    val radiant_gold_adv: List<Int>?,
    val radiant_xp_adv: List<Int>?,

    val start_time: Long,
    val radiant_team: Team?,
    val dire_team: Team?,
    val players: List<Player>
)