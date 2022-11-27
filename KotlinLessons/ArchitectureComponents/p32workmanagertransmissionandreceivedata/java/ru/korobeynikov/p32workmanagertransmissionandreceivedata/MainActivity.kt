package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker1::class.java).build()
        val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorker2::class.java).build()
        val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorker3::class.java)
            .setInputMerger(ArrayCreatingInputMerger::class.java).build()
        val myWorkRequest4 = OneTimeWorkRequest.Builder(MyWorker4::class.java)
            .setInputMerger(MyMerger::class.java).build()
        val workRequests12 = listOf(myWorkRequest1, myWorkRequest2)
        val workRequests34 = listOf(myWorkRequest3, myWorkRequest4)
        WorkManager.getInstance().beginWith(workRequests12).then(workRequests34).enqueue()
    }
}