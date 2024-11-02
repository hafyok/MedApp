package com.example.workmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicaments")
data class MedicamentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val amount: Float
)
