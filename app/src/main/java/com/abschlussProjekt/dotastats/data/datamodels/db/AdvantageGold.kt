package com.abschlussProjekt.dotastats.data.datamodels.db

import androidx.room.Entity

@Entity(tableName = "Advantage_gold",
    primaryKeys = ["match_id","minute"])
data class AdvantageGold(
    val match_id: Long,
    val minute: Int,
    val amount: Int
)