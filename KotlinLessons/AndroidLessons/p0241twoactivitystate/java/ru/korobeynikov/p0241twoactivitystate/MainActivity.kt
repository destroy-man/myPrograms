package ru.korobeynikov.p0241twoactivitystate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    val TAG="States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnActTwo.setOnClickListener(this)
        Log.d(TAG,"MainActivity: onCreate()")
    }

    protected override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"MainActivity: onRestart()")
    }

    protected override fun onStart() {
        super.onStart()
        Log.d(TAG,"MainActivity: onStart()")
    }

    protected override fun onResume() {
        super.onResume()
        Log.d(TAG,"MainActivity: onResume()")
    }

    protected override fun onPause() {
        super.onPause()
        Log.d(TAG,"MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"MainActivity: onDestroy()")
    }

    override fun onClick(p0: View?) {
        val intent=Intent(this,ActivityTwo::class.java)
        startActivity(intent)
    }
}