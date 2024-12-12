package com.example.workmanager.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workmanager.ui.theme.Green
import com.example.workmanager.ui.theme.LightGreen

@Composable
@Preview(showSystemUi = true)
fun TopCard() {
    val colorStops = arrayOf(
        0.0f to LightGreen,
        1f to Green
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f),
        shape = RoundedCornerShape(bottomStart = 33.dp, bottomEnd = 33.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops[0],
                        colorStops[1],
                    )
                )
        )
        {
            Text(text = "Card Gradient Background")
        }

    }
}