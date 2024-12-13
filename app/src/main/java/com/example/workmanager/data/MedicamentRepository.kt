package com.example.workmanager.data

interface MedicamentRepository {
    fun insertMedicament(medicament: MedicamentEntity)

    fun getLastMedicamentId(): Int
}
