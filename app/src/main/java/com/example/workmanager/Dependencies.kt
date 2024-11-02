package com.example.workmanager

import android.content.Context
import androidx.room.Room
import com.example.workmanager.data.AppDB
import com.example.workmanager.data.MedicamentRepository

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context){
        applicationContext = context
    }

    private val appDatabase: AppDB by lazy {
        Room.databaseBuilder(applicationContext, AppDB::class.java, "database.db")
            .build()
    }

    val medicamentRepository: MedicamentRepository by lazy { MedicamentRepository(appDatabase.getMedicamentDao()) }
}