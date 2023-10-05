package com.abschlussProjekt.dotastats.data.datamodels.db.constants

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class Hero(
    @PrimaryKey
    val id: Long,
    @Json(name ="localized_name")
    val name: String,
    val primary_attr: String,
    val img: String,
)