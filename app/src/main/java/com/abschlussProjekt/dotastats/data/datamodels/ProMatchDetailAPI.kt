package com.abschlussProjekt.dotastats.data.datamodels

data class ProMatchDetailAPI(
    val match_id: Long,
    val duration: Int,

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
    val players: List<PlayerAPI>
) {
    fun toProMatchDetail(playerList: List<Player>) = ProMatchDetail(
        match_id,
        duration,
        radiant_score,
        radiant_team_id,
        dire_score,
        dire_team_id,
        radiant_win,
        radiant_gold_adv,
        radiant_xp_adv,
        start_time,
        radiant_team,
        dire_team,
        playerList
    )
}