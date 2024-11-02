package com.example.workmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicament(med: MedicamentEntity)

    @Query("SELECT * FROM medicaments")
    fun getMedicaments(): List<MedicamentEntity>
}