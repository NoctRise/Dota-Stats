package com.abschlussProjekt.dotastats.data.datamodels.api

import com.abschlussProjekt.dotastats.data.datamodels.db.ProMatchDetailDB

data class ProMatchDetailAPI(
    val match_id: Long,
    val duration: Int,
    //val game_mode: Int,
    //val leagueid: Long,
//    val pick_bans,

    val radiant_score: Int,
    val radiant_team_id: Long,
    val dire_score: Int,
    val dire_team_id: Long,
    val radiant_win: Boolean,
    val radiant_gold_adv: List<Int>?,
    val radiant_xp_adv: List<Int>?,

    val start_time: Long,
    val radiant_team: TeamAPI,
    val dire_team: TeamAPI,
    val players: List<PlayerAPI>

) {
    fun toProMatchDetailDB(): ProMatchDetailDB =
        ProMatchDetailDB(
            match_id,
            duration,
            radiant_score,
            radiant_team_id,
            radiant_team.name,
            radiant_team.logo_url,
            radiant_win,
            dire_score,
            dire_team_id,
            dire_team.name,
            dire_team.logo_url,
            start_time
        )
}