package ru.korobeynikov.p13modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val modifierSize = Modifier.size(100.dp)
val modifierBackground = Modifier.background(Color.Green)

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Some text",
            modifier = modifierSize
                .then(modifierBackground)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun HomeScreenButtonWithoutModifier() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Some text")
        Spacer(modifier = Modifier.height(8.dp))
        MyButtonWithModifier(text = "Button") {}
    }
}

@Composable
fun HomeScreenMyButtonModifier() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Some text")
        Spacer(modifier = Modifier.height(8.dp))
        MyButtonWithModifier(
            text = "Button",
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.CenterHorizontally)
        ) {}
    }
}

@Composable
fun MyButtonWithModifier(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = modifier
            .clickable(onClick = onClick)
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(8.dp)
    )
}

@Composable
fun HomeScreenMyButtonWidth() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Some text")
        Spacer(modifier = Modifier.height(8.dp))
        MyButtonWithWidth(text = "Button", width = 100.dp) {}
    }
}

@Composable
fun MyButtonWithWidth(text: String, width: Dp, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(width)
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(8.dp)
    )
}

@Composable
fun HomeScreenMyButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Some text")
        Spacer(modifier = Modifier.height(8.dp))
        MyButton(text = "Button") {}
    }
}

@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .clickable(onClick = onClick)
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(8.dp)
    )
}

@Composable
fun HomeScreen2Border() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(2.dp, Color.Red)
            .padding(32.dp)
            .border(2.dp, Color.Black)
            .background(Color.Green)
    ) {
        Text(text = "Some text", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenPaddingBorderBackground() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(32.dp)
            .border(2.dp, Color.Black)
            .background(Color.Green)
    ) {
        Text(text = "Some text", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenBorderBackgroundPadding() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(2.dp, Color.Black)
            .background(Color.Green)
            .padding(32.dp)
    ) {
        Text(text = "Some text", fontSize = 30.sp)
    }
}

@Composable
fun HomeScreenBorderPaddingBackground() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(2.dp, Color.Black)
            .padding(32.dp)
            .background(Color.Green)
    ) {
        Text(text = "Some text", fontSize = 30.sp)
    }
}