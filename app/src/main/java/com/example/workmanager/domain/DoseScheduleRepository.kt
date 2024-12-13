package com.example.workmanager.domain

import com.example.workmanager.data.DoseScheduleEntity

interface DoseScheduleRepository {
    fun insertDoseSchedule(item: DoseScheduleEntity)
}