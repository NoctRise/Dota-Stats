package com.abschlussProjekt.dotastats.data.datamodels

data class ProMatch(
    val match_id: Long,
    val duration: Int,
    val start_time: Long,
    val radiant_team_id: Long?,
    val radiant_name: String?,
    val dire_team_id: Long?,
    val dire_name: String?,
    val leagueid: Long,
    val league_name: String,
    val radiant_score: Int,
    val dire_score: Int,
    val radiant_win: Boolean
)