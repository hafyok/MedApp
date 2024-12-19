package com.example.workmanager.presentation

import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workmanager.data.DoseScheduleEntity
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.myUiKit.LargeText
import com.example.workmanager.myUiKit.MediumButton
import com.example.workmanager.myUiKit.ProjectOutlinedTextField
import com.example.workmanager.myUiKit.SingleChoiceSegmentedButton
import com.example.workmanager.myUiKit.sansFamily
import com.example.workmanager.ui.theme.BackgroundPurple
import com.example.workmanager.ui.theme.Black
import com.example.workmanager.ui.theme.MainPurple
import com.example.workmanager.ui.theme.White
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
        derivedStateOf {
            selectedTime?.let {
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, it.hour)
                    set(Calendar.MINUTE, it.minute)
                }.time.time
            } ?: 0L
        }
    }

    if (showDialog.value) {
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
    timeText: String,
    dialogColor: Color = BackgroundPurple // По умолчанию серый
) {
    val txtField = remember { mutableStateOf(value) }
    val txtDose = remember { mutableStateOf("") }
    val isDone by remember { derivedStateOf { txtField.value.isNotEmpty() && txtDose.value.isNotEmpty() } }
    val typeDose = remember { mutableStateOf("шт") }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = dialogColor // Используем цвет из параметра
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
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
                    //Spacer(modifier = Modifier.height(20.dp))

                    ProjectOutlinedTextField(textState = txtField, placeholder = "Название")

                    Spacer(modifier = Modifier.height(20.dp))

                    LargeText(text = "Дозировка", modifier = Modifier.padding(vertical = 8.dp))

                    SingleChoiceSegmentedButton(typeDose = typeDose)

                    ProjectOutlinedTextField(
                        textState = txtDose,
                        placeholder = "Кол-во ${typeDose.value}",
                        leadingIcon = Icons.Filled.CheckCircle,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(20.dp))


                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // TODO() Перенести в strings
                        MediumButton(
                            text = "Без напоминаний",
                            onClick = { setTimePicker(true) },
                            colors = CardColors(
                                containerColor = White,
                                contentColor = Black,
                                disabledContentColor = Black,
                                disabledContainerColor = Black
                            )
                        )


                        // TODO() Перенести в strings
                        MediumButton(
                            text = "Выбрать время",
                            onClick = { setTimePicker(true) },
                            colors = CardColors(
                                containerColor = MainPurple,
                                contentColor = Black,
                                disabledContentColor = Black,
                                disabledContainerColor = Black
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Кнопка "Готово"
                    if (isDone) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.clickable {
                                setValue(txtField.value)
                                addMedicament(txtField.value)
                                addSchedule()
                                setShowDialog(false)
                            },
                            colors = CardColors(
                                containerColor = MainPurple,
                                contentColor = Black,
                                disabledContentColor = Black,
                                disabledContainerColor = Black
                            )
                        ) {
                            Text(
                                text = "Готово",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 10.dp)
                                    .fillMaxWidth(),
                                fontFamily = sansFamily,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewAddDialog() {
    val timeInDB = 78L
    AddDialog(
        value = "",
        setShowDialog = { },
        setValue = {},
        addMedicament = {
        },
        addSchedule = {

        },
        setTimePicker = { },
        timeText = if (timeInDB != 0L) {
            val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(timeInDB))
            "Выбранное время: ${dateString}"
        } else {
            "Время не выбрано"
        }
    )
}