package com.example.workmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.domain.DoseScheduleRepository
import com.example.workmanager.domain.MedicamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicamentViewModel @Inject constructor(
    private val medicamentRepository: MedicamentRepository,
    private val doseScheduleRepository: DoseScheduleRepository
) : ViewModel() {
    val medicaments = medicamentRepository.getMedicaments()

    fun insertMedicamentInDb(medicamentEntity: MedicamentEntity) = viewModelScope.launch {
        medicamentRepository.insertMedicament(medicamentEntity)
    }

    fun insertDoseScheduleInDb(schedule: DoseScheduleEntity) = viewModelScope.launch {
        doseScheduleRepository.insertDoseSchedule(schedule)
    }

    fun getLastId() = viewModelScope.async {
        return@async medicamentRepository.getLastMedicamentId()
    }
}