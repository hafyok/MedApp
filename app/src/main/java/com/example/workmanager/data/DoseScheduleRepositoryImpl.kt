package com.example.workmanager.data

import com.example.workmanager.domain.DoseScheduleRepository
import javax.inject.Inject

class DoseScheduleRepositoryImpl @Inject constructor(private val appDB: AppDB): DoseScheduleRepository {
    override fun insertDoseSchedule(item: DoseScheduleEntity) {
        appDB.dao.insertDoseShedule(item)
    }
}