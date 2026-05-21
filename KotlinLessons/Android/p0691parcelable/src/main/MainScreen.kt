package ru.korobeynikov.p0691parcelable.main

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.korobeynikov.p0691parcelable.Constants
import ru.korobeynikov.p0691parcelable.MyObject
import ru.korobeynikov.p0691parcelable.R
import ru.korobeynikov.p0691parcelable.second.SecondActivity

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Column {
        Text(stringResource(R.string.main))
        Button(onClick = {
            val myObj = MyObject("text", 1)
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(MyObject::class.simpleName, myObj)
            Log.d(Constants.LOG_TAG, "startActivity")
            context.startActivity(intent)
        }) {
            Text(stringResource(R.string.send))
        }
    }
}