package com.abschlussProjekt.dotastats.data.datamodels

import androidx.room.Embedded
import androidx.room.Relation
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageEXP
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageGold
import com.abschlussProjekt.dotastats.data.datamodels.db.PlayerDB
import com.abschlussProjekt.dotastats.data.datamodels.db.ProMatchDetailDB

data class ProMatchWithDetails(
    @Embedded
    val proMatchDetailDB: ProMatchDetailDB,

    @Relation(
        parentColumn = "match_id",
        entityColumn = "match_id"
    )
    val radiant_gold_adv: List<AdvantageGold>?,

    @Relation(
        parentColumn = "match_id",
        entityColumn = "match_id"
    )
    val radiant_exp_adv: List<AdvantageEXP>?,

    @Relation(
        parentColumn = "match_id",
        entityColumn = "match_id"
    )
    val players: List<PlayerDB>

    )
