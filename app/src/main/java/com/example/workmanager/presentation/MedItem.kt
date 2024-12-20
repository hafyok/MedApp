package com.example.workmanager.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workmanager.R
import com.example.workmanager.data.MedicamentEntity
import com.example.workmanager.myUiKit.NormalText
import com.example.workmanager.myUiKit.ShortButton
import com.example.workmanager.ui.theme.Black
import com.example.workmanager.ui.theme.LightPurple
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun MedItem(item: MedicamentEntity) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.45f),
        colors = CardColors(
            containerColor = LightPurple,
            contentColor = Black,
            disabledContentColor = Black,
            disabledContainerColor = Black
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                Image(
                    modifier = Modifier.padding(8.dp),
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.ic_flask),
                    contentDescription = "flask"
                )

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    NormalText(text = item.name)
                    Row {
                        Image(
                            modifier = Modifier.padding(top = 6.dp, end = 6.dp),
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "time"
                        )
                        NormalText(text = SimpleDateFormat("HH:mm").format(Date(item.time)))
                    }
                    Row {
                        Image(
                            modifier = Modifier.padding(top = 6.dp, end = 6.dp),
                            painter = painterResource(id = R.drawable.ic_dose),
                            contentDescription = "time"
                        )
                        val itemName = if (item.amount.toString().last()
                                .toString() == "0"
                        ) item.amount.toString().substringBefore(".") else item.amount.toString()
                        NormalText(text = "$itemName ${item.typeDose}")
                    }
                }

            }
            ShortButton(
                text = "Редактировать",
                onClick = { TODO() },
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun PreviewMedItem() {
    MedItem(item = MedicamentEntity(0, "НурофенAd", 4f, 100000L, "шт"))
}