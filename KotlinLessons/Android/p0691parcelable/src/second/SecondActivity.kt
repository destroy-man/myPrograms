package ru.korobeynikov.p0691parcelable.second

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p0691parcelable.Constants
import ru.korobeynikov.p0691parcelable.MyObject

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                SecondScreen()
            }
        }
        Log.d(Constants.LOG_TAG, "getParcelableExtra")
        val myObj = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MyObject::class.simpleName, MyObject::class.java)
        } else {
            intent.getParcelableExtra(MyObject::class.simpleName)
        }
        Log.d(Constants.LOG_TAG, "myObj: ${myObj?.s}, ${myObj?.i}")
    }
}