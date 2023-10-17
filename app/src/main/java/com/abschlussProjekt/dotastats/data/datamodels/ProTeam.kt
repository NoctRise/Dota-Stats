package com.abschlussProjekt.dotastats.data.datamodels

data class ProTeam(
    val team_id: Long,
    val name: String,
    val tag: String,
    val logo_url: String?,
    val rating: Double,
    val wins: Int,
    val losses: Int,
    val last_match_time : Long
)
