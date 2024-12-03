package com.example.workmanager.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "NotificationLogs",
    foreignKeys = [
        ForeignKey(
            entity = DoseScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["scheduleId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NotificationLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val medicamentId: Int,
    //val userId: Int,
    val scheduleId: Int,
    val sentTime: Long,
    val status: String
) {
}

/*
enum class LogStatus{
    SENT, DISMISSED, CONFIRMED
}*/
