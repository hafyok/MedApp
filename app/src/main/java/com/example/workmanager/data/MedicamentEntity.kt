package com.example.workmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workmanager.domain.Medicament

@Entity(tableName = "medicaments")
data class MedicamentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val amount: Float
){
    fun toMedicament(): Medicament = Medicament(
        id = id,
        name = name,
        amount = amount
    )
}
