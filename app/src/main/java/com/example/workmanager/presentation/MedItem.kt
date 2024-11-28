package com.example.workmanager.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workmanager.data.MedicamentEntity


@Composable
fun MedItem(item: MedicamentEntity) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(BorderStroke(width = 2.dp, color = Color.Gray), shape = RoundedCornerShape(25)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
        Text(text = item.amount.toString(), modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewMedItem(){
    MedItem(item = MedicamentEntity(0, "DFKDKFD", 4f, 100000L))
}