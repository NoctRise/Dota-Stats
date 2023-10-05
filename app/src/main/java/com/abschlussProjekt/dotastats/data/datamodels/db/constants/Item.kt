package com.abschlussProjekt.dotastats.data.datamodels.db.constants

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Item(
    @PrimaryKey
    val id: Long,
    @Json(name = "dname")
    val name: String?,
    val img: String


)