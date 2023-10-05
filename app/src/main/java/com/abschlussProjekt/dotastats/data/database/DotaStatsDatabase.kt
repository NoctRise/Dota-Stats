package com.abschlussProjekt.dotastats.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageEXP
import com.abschlussProjekt.dotastats.data.datamodels.db.AdvantageGold
import com.abschlussProjekt.dotastats.data.datamodels.db.PlayerDB
import com.abschlussProjekt.dotastats.data.datamodels.db.ProMatchDetailDB
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.db.constants.Item

@Database(
    entities = [
        Hero::class, Item::class, Ability::class, ProMatchDetailDB::class,
        AdvantageGold::class, AdvantageEXP::class, PlayerDB::class
    ],
    version = 1
)
abstract class DotaStatsDatabase : RoomDatabase() {

    abstract val dotaStatsDao: DotaStatsDao
}

private lateinit var INSTANCE: DotaStatsDatabase

fun getInstance(context: Context): DotaStatsDatabase {
    synchronized(DotaStatsDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DotaStatsDatabase::class.java,
                "DotaStats_Database"
            ).build()
        }
    }
    return INSTANCE
}