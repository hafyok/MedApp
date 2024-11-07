package com.example.workmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [MedicamentEntity::class], )
abstract class AppDB: RoomDatabase() {
    abstract val dao: MedicamentDao
}