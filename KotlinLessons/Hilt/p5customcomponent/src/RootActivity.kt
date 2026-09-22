package ru.korobeynikov.p5customcomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    @Inject
    lateinit var myComponentManager: MyComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            myComponentManager.create()
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("This is root activity")
                Text("my component = ${myComponentManager.get().hashCode()}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            myComponentManager.destroy()
        }
    }
}