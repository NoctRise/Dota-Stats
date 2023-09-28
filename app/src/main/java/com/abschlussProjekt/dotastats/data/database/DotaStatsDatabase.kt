package com.abschlussProjekt.dotastats.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item

@Database(entities = [Hero::class, Item::class,Ability::class], version = 1)
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