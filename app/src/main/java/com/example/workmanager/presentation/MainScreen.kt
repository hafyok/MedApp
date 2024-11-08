package com.example.workmanager.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val medicaments by viewModel.medicaments.observeAsState(emptyList())
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AddDialog(
            value = "",
            setShowDialog = { showDialog.value = it },
            setValue = {},
            addMedicament = { name ->
                viewModel.insertMedicamentInDb(MedicamentEntity(0, name, 2f))
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog.value = true }) {
                Text(text = "Добавить лекарство", modifier = Modifier.padding(8.dp))
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(medicaments) { item ->
                    Text(text = item.name)
                }
            }
        }
    }
}