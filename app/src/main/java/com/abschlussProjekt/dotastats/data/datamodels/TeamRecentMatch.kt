package com.abschlussProjekt.dotastats.data.datamodels

data class TeamRecentMatch(
    val match_id: Long,
    val radiant_win: Boolean,
    val radiant_score: Int,
    val dire_score: Int,
    val radiant: Boolean,
    val duration: Int,
    val start_time: Long,
    val opposing_team_id: Long,
    val opposing_team_name: String,
    val opposing_team_logo: String?
)