package com.example.workmanager.domain

import com.example.workmanager.data.MedicamentEntity

data class Medicament(
    val id: Int,
    val name: String,
    val amount: Float
){
    fun toMedicamentEntity(): MedicamentEntity = MedicamentEntity(
        id = 0,
        name = name,
        amount = amount
    )
}
