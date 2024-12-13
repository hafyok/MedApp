package com.example.workmanager.domain

import androidx.lifecycle.LiveData
import com.example.workmanager.data.MedicamentEntity

interface MedicamentRepository {
    fun getMedicaments(): LiveData<List<MedicamentEntity>>

    fun insertMedicament(medicament: MedicamentEntity)

    fun getLastMedicamentId(): Int
}
