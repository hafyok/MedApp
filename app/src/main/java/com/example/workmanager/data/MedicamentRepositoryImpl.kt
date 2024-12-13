package com.example.workmanager.data

import androidx.lifecycle.LiveData
import com.example.workmanager.domain.MedicamentRepository
import javax.inject.Inject

class MedicamentRepositoryImpl @Inject constructor(
    private val appDB: AppDB
) : MedicamentRepository {
    override fun getMedicaments(): LiveData<List<MedicamentEntity>> {
        return appDB.dao.getMedicaments()
    }

    override fun insertMedicament(medicament: MedicamentEntity) {
        appDB.dao.insertMedicament(medicament)
    }

    override fun getLastMedicamentId(): Int {
        return appDB.dao.getLastElement()
    }
}