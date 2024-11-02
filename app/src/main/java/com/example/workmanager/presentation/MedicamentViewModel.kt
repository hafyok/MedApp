package com.example.workmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmanager.Dependencies
import com.example.workmanager.data.MedicamentEntity
import kotlinx.coroutines.launch

class MedicamentViewModel: ViewModel() {
    val medicamentRepository = Dependencies.medicamentRepository

    fun insertMedicamentInDb(medicamentEntity: MedicamentEntity){
        viewModelScope.launch {
            medicamentRepository.insertMedicament(medicamentEntity)
        }
    }
}