package ru.korobeynikov.p181guidelinesbarrierschains

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun MainScreen() {
    //Chains
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text, button) = createRefs()
        val horizontalChain = createHorizontalChain(button, text)
        val verticalChain = createVerticalChain(button, text, chainStyle = ChainStyle.Packed)
        Text("Text", modifier = Modifier.constrainAs(text) {})
        Button(modifier = Modifier.constrainAs(button) {}, onClick = {}) {
            Text("Button")
        }
    }
}

@Composable
fun BarrierScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text, button) = createRefs()
        val topBarrier = createTopBarrier(text, button)
        Text("Text", modifier = Modifier.constrainAs(text) {
            bottom.linkTo(button.top)
        })
        Button(modifier = Modifier.constrainAs(button) {
            bottom.linkTo(topBarrier)
        }, onClick = {}) {
            Text("Button")
        }
    }
}

@Composable
fun GuidelinesScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val startGuideline = createGuidelineFromStart(0.2f)
        val endGuideline = createGuidelineFromEnd(0.2f)
        val topGuideline = createGuidelineFromTop(32.dp)
        val bottomGuideline = createGuidelineFromBottom(32.dp)
        val (textStart, textEnd, textTop, textBottom) = createRefs()
        Text("Text start", modifier = Modifier.constrainAs(textStart) {
            start.linkTo(parent.start)
            end.linkTo(endGuideline)
        })
        Text("Text end", modifier = Modifier.constrainAs(textEnd) {
            start.linkTo(startGuideline)
            end.linkTo(parent.end)
        })
        Text("Text top", modifier = Modifier.constrainAs(textTop) {
            top.linkTo(parent.top)
            bottom.linkTo(bottomGuideline)
        })
        Text("Text bottom", modifier = Modifier.constrainAs(textBottom) {
            top.linkTo(topGuideline)
            bottom.linkTo(parent.bottom)
        })
    }
}

@Composable
fun ConstraintSetScreen() {
    BoxWithConstraints {
        val constraints = if (minWidth < 600.dp) {
            decoupledConstraints(16.dp)
        } else {
            decoupledConstraints(32.dp)
        }

        ConstraintLayout(constraints) {
            Button(modifier = Modifier.layoutId("button"), onClick = {}) {
                Text("Button")
            }
            Text("Text", modifier = Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
        }
    }
}