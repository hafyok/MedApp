package com.example.workmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoseScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val medicamentId: Int,
    val userId: Int,
    val dosage: Float,
    val time: Long,
    val frequency: Int,
    val endDate: Long
){

}