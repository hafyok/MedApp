package com.example.workmanager.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showSystemUi = true)
fun TopCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Green,
        ),
        shape = RoundedCornerShape(bottomStart = 33.dp ,bottomEnd = 33.dp)
    ) {
        Text(text = "Hello")
    }
}