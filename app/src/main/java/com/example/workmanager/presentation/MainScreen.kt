package com.example.workmanager.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workmanager.data.MedicamentEntity

@Preview
@Composable
fun MainScreen(
    viewModel: MedicamentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val medicaments = viewModel.medimcaments.value

    LaunchedEffect(Unit) {
        viewModel.getMedicamentFromDb()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.insertMedicamentInDb(
                    MedicamentEntity(
                        0,
                        "Панацея",
                        2f
                    )
                )
            })
            {
                Text(text = "Добавить лекарство", modifier = Modifier.padding(8.dp))
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(medicaments){ item ->
                    Text(text = item.name)

                }
            }
        }
    }
}