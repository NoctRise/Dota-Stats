package com.abschlussProjekt.dotastats.data.datamodels.constants

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Ability(
    @PrimaryKey
    var id: Long?,
    @Json(name = "dname")
    val name: String?,
    val img: String?,
    var skill_id_name: String?
)