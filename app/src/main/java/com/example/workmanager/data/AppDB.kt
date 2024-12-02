package com.example.workmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 2, entities = [MedicamentEntity::class, DoseScheduleEntity::class], )
abstract class AppDB: RoomDatabase() {
    abstract val dao: MedicamentDao
}