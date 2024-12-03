package com.example.workmanager.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workmanager.data.AppDB
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicamentViewModel @Inject constructor(
    val appDB: AppDB
): ViewModel() {
    val medicaments: LiveData<List<MedicamentEntity>> = appDB.dao.getMedicaments()

    fun insertMedicamentInDb(medicamentEntity: MedicamentEntity) = viewModelScope.launch{
        appDB.dao.insertMedicament(medicamentEntity)
    }

    fun insertDoseScheduleInDb(schedule: DoseScheduleEntity) = viewModelScope.launch {
        appDB.dao.insertDoseShedule(schedule)
    }

    /*fun getMedicamentFromDb() = viewModelScope.launch {
        medimcaments.value = appDB.dao.getMedicaments()
    }*/
}