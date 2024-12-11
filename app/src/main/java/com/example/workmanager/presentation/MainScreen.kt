package com.example.workmanager.presentation

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workmanager.R
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.ui.theme.LightBlueBackground
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MedicamentViewModel = hiltViewModel()
) {
    val medicaments by viewModel.medicaments.observeAsState(emptyList())
    val showDialog = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    val timeInDB by remember {
        derivedStateOf {
            if (selectedTime != null) {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
                cal.set(Calendar.MINUTE, selectedTime!!.minute)
                cal.isLenient = false
                cal.time.time
            } else {
                0L
            }
        }
    }

    if (showDialog.value) {
        AddDialog(
            value = "",
            setShowDialog = { showDialog.value = it },
            setValue = {},
            addMedicament = { name ->
                viewModel.insertMedicamentInDb(MedicamentEntity(0, name, 2f, timeInDB))
                viewModel.insertDoseScheduleInDb(
                    DoseScheduleEntity(
                        0,
                        6,
                        0.5f,
                        1210239434L,
                        2,
                        1210299434L
                    )
                )
            },
            addSchedule = {

            },
            setTimePicker = { showTimePicker.value = it },
            timeText = if (timeInDB != 0L) {
                val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(timeInDB))
                "Выбранное время: ${dateString}"
            } else {
                "Время не выбрано"
            }
        )
    }

    if (showTimePicker.value) {
        DialTime(
            onConfirm = { time ->
                selectedTime = time
                showTimePicker.value = false
            },
            onDismiss = { /*TODO*/ },
            setShowDialog = { showTimePicker.value = it }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(LightBlueBackground),
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog.value = true }) {
                Text(text = "Добавить лекарство", modifier = Modifier.padding(8.dp))
            }
        },
        containerColor = LightBlueBackground


    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {

            val sansFamily = FontFamily(
                Font(R.font.productsans, FontWeight.Normal)
            )
            // Возможно лучше перенести TopCard в topBar в Scaffold
            TopCard()
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(
                    text = "Расписание приёма лекарств: ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(8.dp),
                    fontFamily = sansFamily
                )
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(medicaments) { item ->
                        MedItem(item = item)
                    }
                }
            }
        }

    }
}