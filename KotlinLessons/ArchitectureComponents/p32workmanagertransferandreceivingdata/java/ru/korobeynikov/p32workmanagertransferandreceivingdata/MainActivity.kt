package ru.korobeynikov.p32workmanagertransferandreceivingdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.korobeynikov.p32workmanagertransferandreceivingdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker1::class.java).build()
        val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorker2::class.java).build()
        val myWorkRequest3 =
            OneTimeWorkRequest.Builder(MyWorker3::class.java).setInputMerger(MyMerger::class.java).build()
        val myWorkRequest4 =
            OneTimeWorkRequest.Builder(MyWorker4::class.java).setInputMerger(MyMerger::class.java).build()
        WorkManager.getInstance(this).beginWith(listOf(myWorkRequest1, myWorkRequest2))
            .then(listOf(myWorkRequest3, myWorkRequest4)).enqueue()
    }
}