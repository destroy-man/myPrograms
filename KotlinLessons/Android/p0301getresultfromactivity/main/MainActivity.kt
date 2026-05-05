package ru.korobeynikov.p0301getresultfromactivity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import ru.korobeynikov.p0301getresultfromactivity.Constants
import ru.korobeynikov.p0301getresultfromactivity.align.AlignActivity
import ru.korobeynikov.p0301getresultfromactivity.color.ColorActivity

class MainActivity : ComponentActivity() {

    var color by mutableStateOf(Color.Black)
    var alignment by mutableStateOf(Alignment.CenterHorizontally)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen(color, alignment) { requestCode ->
                    val intent = if (requestCode == Constants.REQUEST_CODE_COLOR) {
                        Intent(context, ColorActivity::class.java)
                    } else {
                        Intent(context, AlignActivity::class.java)
                    }
                    resultLauncher.launch(intent)
                }
            }
        }
    }

    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { data ->
            val requestCode = data.getIntExtra("requestCode", 0)
            Log.d(
                "myLogs",
                "requestCode = $requestCode, resultCode = ${result.resultCode}"
            )
            if (result.resultCode == RESULT_OK) {
                when (requestCode) {
                    Constants.REQUEST_CODE_COLOR -> {
                        color = when (data.getStringExtra("color")) {
                            "red" -> Color.Red
                            "green" -> Color.Green
                            else -> Color.Blue
                        }
                    }

                    Constants.REQUEST_CODE_ALIGN -> {
                        alignment = when (data.getStringExtra("alignment")) {
                            "start" -> Alignment.Start
                            "center" -> Alignment.CenterHorizontally
                            else -> Alignment.End
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Wrong result",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}