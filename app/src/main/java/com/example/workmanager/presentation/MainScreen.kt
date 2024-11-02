package com.example.workmanager.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.workmanager.data.MedicamentEntity

@Composable
fun MainScreen(viewModel: MedicamentViewModel = MedicamentViewModel(), modifier: Modifier = Modifier){
    Column {
        Button(onClick = { viewModel.insertMedicamentInDb(MedicamentEntity(0, "Панацея", 2f))}) {
            Text(text = "Добавить лекарство")
        }
    }
}