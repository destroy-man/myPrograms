package ru.korobeynikov.p0301getresultfromactivity.color

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p0301getresultfromactivity.Constants

class ColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                ColorScreen { color ->
                    val intent = Intent()
                    intent.putExtra("requestCode", Constants.REQUEST_CODE_COLOR)
                    intent.putExtra("color", color)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}