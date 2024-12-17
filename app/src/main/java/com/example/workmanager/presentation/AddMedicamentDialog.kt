package com.example.workmanager.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.myUiKit.LargeText
import com.example.workmanager.myUiKit.NormalText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicamentDialog(
    viewModel: MedicamentViewModel = hiltViewModel(),
    showDialogState: MutableState<Boolean>,
) {
    val showDialog = showDialogState
    val showTimePicker = remember { mutableStateOf(false) }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

    val timeInDB by remember {
        derivedStateOf{
            selectedTime?.let {
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, it.hour)
                    set(Calendar.MINUTE, it.minute)
                }.time.time
            } ?: 0L
        }
    }

    if (showDialog.value){
        AddDialog(
            value = "",
            setShowDialog = { showDialog.value = it },
            setValue = {},
            addMedicament = { name ->
                viewModel.insertMedicamentInDb(MedicamentEntity(0, name, 2f, timeInDB))
            },
            addSchedule = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertDoseScheduleInDb(
                        DoseScheduleEntity(
                            id = 0,
                            medicamentId = viewModel.getLastId().await(),
                            dosage = 0.5f,
                            time = 1210239434L,
                            frequency = 2,
                            endDate = 1210299434L
                        )
                    )

                    viewModel.scheduleNotification(timeInDB)
                }
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
            setShowDialog = { showTimePicker.value = it }
        )
    }


}

// TODO() Допилить UI ВСЕГО диалога
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    addMedicament: (String) -> Unit,
    addSchedule: () -> Unit,
    setTimePicker: (Boolean) -> Unit,
    timeText: String
) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Gray
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LargeText(text = "Добавить лекарство")
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable { setShowDialog(false) }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = txtField.value,
                        onValueChange = { txtField.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(
                                        id = if (txtFieldError.value.isEmpty()) android.R.color.holo_green_light
                                        else android.R.color.holo_red_dark
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        shape = RoundedCornerShape(50),
                        colors = TextFieldDefaults.textFieldColors(
                            //backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),

                        )
                    NormalText(text = timeText)
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (txtField.value.isEmpty()) {
                                txtFieldError.value = "Field can not be empty"
                                return@Button
                            }
                            setValue(txtField.value)
                            addMedicament(txtField.value)
                            addSchedule()
                            setShowDialog(false)
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        NormalText(text = "Done")
                    }

                    Button(
                        onClick = {
                            setTimePicker(true)
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        NormalText(text = "Select time")
                    }
                }
            }

        }
    }
}