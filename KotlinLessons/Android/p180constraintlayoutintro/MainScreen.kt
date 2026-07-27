package ru.korobeynikov.p180constraintlayoutintro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MainScreen() {
    //ConstraintLayout with two-way binding (start-end)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text, button) = createRefs()
        Text(stringResource(R.string.text_element), modifier = Modifier.constrainAs(text) {
            start.linkTo(parent.start, margin = 8.dp)
            end.linkTo(button.start)
        })
        Button(modifier = Modifier.constrainAs(button) {
            end.linkTo(parent.end, margin = 46.dp)
            top.linkTo(parent.top, margin = 40.dp)
        }, onClick = {}) {
            Text(stringResource(R.string.button_text))
        }
    }
}

@Composable
fun ConstraintLayoutFewElementsScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text, button, checkbox) = createRefs()
        Text(stringResource(R.string.text_element), modifier = Modifier.constrainAs(text) {
            start.linkTo(parent.start, margin = 73.dp)
            top.linkTo(parent.top, margin = 120.dp)
        })
        Button(modifier = Modifier.constrainAs(button) {
            start.linkTo(text.end, margin = 103.dp)
            top.linkTo(text.bottom, margin = 82.dp)
        }, onClick = {}) {
            Text(stringResource(R.string.button_text))
        }
        Checkbox(checked = false, modifier = Modifier.constrainAs(checkbox) {
            start.linkTo(text.start)
            bottom.linkTo(parent.bottom, margin = 104.dp)
        }, onCheckedChange = {})
    }
}

@Composable
fun ConstraintLayoutOneElementScreen() {
    ConstraintLayout {
        val text = createRef()
        Text(stringResource(R.string.text_element), modifier = Modifier.constrainAs(text) {
            start.linkTo(parent.start, margin = 122.dp)
            top.linkTo(parent.top, margin = 102.dp)
        })
    }
}