package com.korobeynikov.p0291workwithview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {

    suspend fun View.awaitLayoutChange()=suspendCancellableCoroutine<Unit> {cont->
        val listener=object:View.OnLayoutChangeListener{
            override fun onLayoutChange(view: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                view?.removeOnLayoutChangeListener(this)
                cont.resume(Unit)
            }
        }
        addOnLayoutChangeListener(listener)
        cont.invokeOnCancellation{
            removeOnLayoutChangeListener(listener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flow=callbackFlow<String>{
            val textWatcher=object:TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    editable.toString().let{offer(it)}
                }
            }
            editText.addTextChangedListener(textWatcher)
            awaitClose{
                editText.removeTextChangedListener(textWatcher)
            }
        }
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}