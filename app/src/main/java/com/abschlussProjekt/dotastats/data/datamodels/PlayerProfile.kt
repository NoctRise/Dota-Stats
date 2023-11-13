package com.abschlussProjekt.dotastats.data.datamodels

data class PlayerProfile (
    val playerProfileAPI: PlayerProfileAPI,
    val winLose : Map<String,Int>,
    val recentMatches: List<PlayerRecentMatch>
)