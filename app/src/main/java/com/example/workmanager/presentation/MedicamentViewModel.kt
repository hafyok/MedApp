package com.example.workmanager.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmanager.data.AppDB
import com.example.workmanager.data.MedicamentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicamentViewModel @Inject constructor(
    val appDB: AppDB
): ViewModel() {
    val medimcaments = mutableStateOf(emptyList<MedicamentEntity>())

    fun insertMedicamentInDb(medicamentEntity: MedicamentEntity) = viewModelScope.launch{
        appDB.dao.insertMedicament(medicamentEntity)
    }

    fun getMedicamentFromDb() = viewModelScope.launch {
        medimcaments.value = appDB.dao.getMedicaments()
    }
}