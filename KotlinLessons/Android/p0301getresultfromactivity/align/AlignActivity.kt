package ru.korobeynikov.p0301getresultfromactivity.align

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p0301getresultfromactivity.Constants

class AlignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                AlignScreen { alignment ->
                    val intent = Intent()
                    intent.putExtra("requestCode", Constants.REQUEST_CODE_ALIGN)
                    intent.putExtra("alignment", alignment)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}