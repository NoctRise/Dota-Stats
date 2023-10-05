package com.abschlussProjekt.dotastats.data.datamodels.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Match_details")
data class ProMatchDetailDB(
    @PrimaryKey
    val match_id: Long,
    val duration: Int,


    val radiant_score: Int,
    val radiant_team_id: Long,
    val radiant_team_name: String,
    val radiant_team_logo_url : String?,
    val radiant_win: Boolean,


    val dire_score: Int,
    val dire_team_id: Long,
    val dire_team_name: String,
    val dire_team_logo_url: String?,

    val start_time: Long,

)