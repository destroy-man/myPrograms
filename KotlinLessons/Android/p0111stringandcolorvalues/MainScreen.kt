package ru.korobeynikov.p0111stringandcolorvalues

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val bottomColor = ContextCompat.getColor(context, R.color.bottomColor)
    val bottomText = ContextCompat.getString(context, R.string.bottomText)
    val bottomButtonText = ContextCompat.getString(context, R.string.buttonBottomText)
    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.topColor)
                )
        ) {
            Text(
                stringResource(R.string.topText),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            )
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {}) {
                Text(stringResource(R.string.buttonTopText))
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = Color(bottomColor)
                )
        ) {
            Text(
                bottomText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            )
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {}) {
                Text(bottomButtonText)
            }
        }
    }
}