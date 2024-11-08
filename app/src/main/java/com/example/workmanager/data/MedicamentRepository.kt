package com.example.workmanager.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
class MedicamentRepository(private val medicamentDao: MedicamentDao) {
    suspend fun insertMedicament(med: MedicamentEntity) {
        withContext(Dispatchers.IO) {
            medicamentDao.insertMedicament(med)
        }
    }

    suspend fun getAllMeds(): List<MedicamentEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext medicamentDao.getMedicaments()
        }
    }
}*/
