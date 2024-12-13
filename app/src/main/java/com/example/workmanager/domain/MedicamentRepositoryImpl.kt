package com.example.workmanager.domain

import com.example.workmanager.data.AppDB
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.data.MedicamentRepository
import javax.inject.Inject

class MedicamentRepositoryImpl @Inject constructor(
    private val appDB: AppDB
) : MedicamentRepository {

    override fun insertMedicament(medicament: MedicamentEntity) {
        appDB.dao.insertMedicament(medicament)
    }

    override fun getLastMedicamentId(): Int {
        return appDB.dao.getLastElement()
    }
}