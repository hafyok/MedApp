package com.example.workmanager.presentation.MedicamentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.domain.DoseScheduleRepository
import com.example.workmanager.domain.MedicamentRepository
import com.example.workmanager.workers.RescheduleAlarmWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MedicamentViewModel @Inject constructor(
    private val medicamentRepository: MedicamentRepository,
    private val doseScheduleRepository: DoseScheduleRepository,
    private val workManager: WorkManager
) : ViewModel() {
    private val workName = "AlarmWorker"

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


    fun scheduleNotification(timeMillis: Long) {
        /*
            if (timeInMillis < System.currentTimeMillis()) add(Calendar.DAY_OF_MONTH, 1)
        }*/

        val inputData = Data.Builder()
            .putLong("TIME_IN_MILLIS", timeMillis)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<RescheduleAlarmWorker>()
            .setInputData(inputData)
            .build()

        workManager
            .enqueueUniqueWork(workName, ExistingWorkPolicy.REPLACE, workRequest)

        /*WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData("AlarmWorker")
            .observe(this) { workInfos ->
                workInfos?.forEach { workInfo ->
                    Log.d("alrmwrk", "Task state: ${workInfo.state}")
                }
            }*/
    }


}