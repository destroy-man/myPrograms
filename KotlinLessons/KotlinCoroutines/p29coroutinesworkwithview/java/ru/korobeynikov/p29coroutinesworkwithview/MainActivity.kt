package ru.korobeynikov.p29coroutinesworkwithview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.korobeynikov.p29coroutinesworkwithview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        val editText = binding.editText
        val flow = callbackFlow {
            val textWatcher = object : TextWatcher {

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    trySend(editable.toString())
                }
            }
            editText.addTextChangedListener(textWatcher)
            awaitClose {
                editText.removeTextChangedListener(textWatcher)
            }
        }
        CoroutineScope(Job()).launch {
            flow.collect { log(it) }
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}