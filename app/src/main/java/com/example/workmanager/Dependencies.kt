package com.example.workmanager

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.workmanager.data.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Dependencies {

    @Provides
    @Singleton
    fun provideAppDb(app: Application): AppDB{
        return Room.databaseBuilder(
            app,
            AppDB::class.java,
            "database.db"
        )
            .allowMainThreadQueries() // TODO() мб опасно
            .build()
    }

}