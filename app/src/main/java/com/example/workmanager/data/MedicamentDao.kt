package com.example.workmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicament(med: MedicamentEntity)

    @Query("SELECT * FROM medicaments")
    fun getMedicaments(): LiveData<List<MedicamentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoseShedule(value: DoseScheduleEntity)
}