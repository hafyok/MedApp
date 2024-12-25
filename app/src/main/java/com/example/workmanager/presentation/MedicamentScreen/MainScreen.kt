package com.example.workmanager.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workmanager.R
import com.example.workmanager.myUiKit.LargeText
import com.example.workmanager.ui.theme.LightBlueBackground
import com.example.workmanager.ui.theme.White

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("SimpleDateFormat")
@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MedicamentViewModel = hiltViewModel()
) {
    val medicaments by viewModel.medicaments.observeAsState(emptyList())
    val showDialog = remember { mutableStateOf(false) }

    AddMedicamentDialog(showDialogState = showDialog)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true },
                containerColor = White,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .padding(8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "plus"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "tablet"
                    )
                }
            }
        },
        containerColor = LightBlueBackground
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            // Возможно лучше перенести TopCard в topBar в Scaffold
            TopCard()
            Column(modifier = Modifier.padding(innerPadding)) {
                LargeText(text = "Расписание приёма лекарств: ", modifier = Modifier.padding(horizontal = 16.dp))

                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(8.dp)) {
                    items(medicaments) { item ->
                        MedItem(item = item)
                    }
                }
            }
        }

    }
}