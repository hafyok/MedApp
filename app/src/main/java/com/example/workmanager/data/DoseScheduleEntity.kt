package com.example.workmanager.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "DoseSchedule",
    foreignKeys = [
        ForeignKey(
            entity = MedicamentEntity::class,
            parentColumns = ["id"],
            childColumns = ["medicamentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DoseScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val medicamentId: Int,
    //val userId: Int,
    val dosage: Float,
    val time: Long,
    val frequency: Int,
    val endDate: Long
){

}