package com.example.workmanager.myUiKit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workmanager.R
import com.example.workmanager.ui.theme.Black
import com.example.workmanager.ui.theme.MainPurple
import com.example.workmanager.ui.theme.White

val sansFamily = FontFamily(
    Font(R.font.productsans, FontWeight.Normal)
)

@Composable
fun NormalText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = Black,
        fontFamily = sansFamily
    )
}

@Composable
fun LargeText(
    text: String, modifier: Modifier = Modifier
        .padding(horizontal = 8.dp)
        //.fillMaxWidth()
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier,
        fontFamily = sansFamily,
        textAlign = TextAlign.Start
    )
}

@Preview
@Composable
fun ShortButton(
    modifier: Modifier = Modifier,
    text: String = "Button", onClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.clickable { onClick() },
        colors = CardColors(
            containerColor = White,
            contentColor = Black,
            disabledContentColor = Black,
            disabledContainerColor = Black
        )
    ) {
        NormalText(text = text, modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp))
    }
}

@Preview
@Composable
fun MediumButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    onClick: () -> Unit = {},
    colors: CardColors = CardColors(
        containerColor = MainPurple,
        contentColor = Black,
        disabledContentColor = Black,
        disabledContainerColor = Black
    )
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.clickable { onClick() },
        colors = colors
    ) {
        NormalText(text = text, modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp))
    }
}

@Composable
@Preview(showSystemUi = true)
fun ProjectOutlinedTextField(
    textState: MutableState<String> = mutableStateOf(""),
    placeholder: String = "Тест",
    leadingIcon: ImageVector = Icons.Filled.Add,
    description: String? = null,
    modifier: Modifier = Modifier.padding(vertical = 8.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = { textState.value = it },
        leadingIcon = { Icon(leadingIcon, description) },
        label = { NormalText(text = placeholder, modifier = Modifier.alpha(0.5f)) },
        keyboardOptions = keyboardOptions
    )
}

