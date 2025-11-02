package ru.korobeynikov.p39userinputindicationinteractionsource

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    //Interaction custom shape
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        Text(
            text = "Some text",
            fontSize = 30.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 50))
                .clickable(
                    onClick = {},
                    interactionSource = interactionSource,
                    indication = MyIndicationNodeFactory
                )
        )
    }
}

@Composable
fun HomeScreenTextAndBox() {
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        Text(
            text = "Some text",
            fontSize = 30.sp,
            modifier = Modifier.clickable(
                onClick = {},
                interactionSource = interactionSource,
                indication = MyIndicationNodeFactory
            )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray)
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = MyIndicationNodeFactory
                )
        )
    }
}

@Composable
fun HomeScreenFewText() {
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        Text(text = "Some text", fontSize = 15.sp)
        Text(text = "Some text", fontSize = 15.sp)
        Text(
            text = "Some text",
            fontSize = 30.sp,
            modifier = Modifier.clickable(
                onClick = {},
                interactionSource = interactionSource,
                indication = MyIndicationNodeFactory
            )
        )
        Text(text = "Some text", fontSize = 15.sp)
        Text(text = "Some text", fontSize = 15.sp)
    }
}

@Composable
fun HomeScreenInteractionNull() {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = "Some text",
        fontSize = 30.sp,
        modifier = Modifier.clickable(
            onClick = {},
            interactionSource = interactionSource,
            indication = null
        )
    )
}

@Composable
fun HomeScreenIndicationDefault() {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = "Some text",
        fontSize = 30.sp,
        modifier = Modifier.clickable(
            onClick = {},
            interactionSource = interactionSource,
            indication = LocalIndication.current
        )
    )
}

@Composable
fun HomeScreenFontSizePressed() {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState().value
    Text(
        text = "Some text",
        fontSize = if (pressed) 30.sp else 28.sp,
        modifier = Modifier.clickable(
            onClick = {},
            interactionSource = interactionSource,
            indication = LocalIndication.current
        )
    )
}

@Composable
fun HomeScreenIndicationInteractionsAndHoverable() {
    val interactionSource = remember { MutableInteractionSource() }
    val interaction = interactionSource.interactions.collectAsState(initial = null)
    Text(
        text = "Some text ${interaction.value?.javaClass?.simpleName}",
        fontSize = 30.sp,
        modifier = Modifier
            .clickable(
                onClick = {},
                interactionSource = interactionSource,
                indication = LocalIndication.current
            )
            .hoverable(interactionSource = interactionSource)
    )
}