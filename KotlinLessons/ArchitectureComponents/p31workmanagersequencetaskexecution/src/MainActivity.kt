package ru.korobeynikov.p31workmanagersequencetaskexecution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.korobeynikov.p31workmanagersequencetaskexecution.workers.MyWorker1
import ru.korobeynikov.p31workmanagersequencetaskexecution.workers.MyWorker2
import ru.korobeynikov.p31workmanagersequencetaskexecution.workers.MyWorker3
import ru.korobeynikov.p31workmanagersequencetaskexecution.workers.MyWorker4
import ru.korobeynikov.p31workmanagersequencetaskexecution.workers.MyWorker5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker1::class).build()
        val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorker2::class).build()
        val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorker3::class).build()
        val myWorkRequest4 = OneTimeWorkRequest.Builder(MyWorker4::class).build()
        val myWorkRequest5 = OneTimeWorkRequest.Builder(MyWorker5::class).build()
        val workManager = WorkManager.getInstance(this)

        /*Параллельное выполнение задач
        workManager.enqueue(listOf(myWorkRequest1, myWorkRequest2, myWorkRequest3))
         */

        /*Последовательное выполнение задач
        workManager.beginWith(myWorkRequest1)
            .then(myWorkRequest2)
            .then(myWorkRequest3)
            .enqueue()
         */

        /*Комбинация параллельного и последовательного выполнения задач
        workManager.beginWith(listOf(myWorkRequest1, myWorkRequest2))
            .then(listOf(myWorkRequest3, myWorkRequest4))
            .then(myWorkRequest5)
            .enqueue()
         */

        /*Палеллельное выполнение группы задач (1-2 и 3-4) и последовательное выполнение 5 задачи
        после выполнения 1-4 задач

        val chain12 = workManager.beginWith(myWorkRequest1).then(myWorkRequest2)
        val chain34 = workManager.beginWith(myWorkRequest3).then(myWorkRequest4)
        WorkContinuation.combine(listOf(chain12, chain34)).then(myWorkRequest5).enqueue()
         */

        workManager.beginUniqueWork(
            "work123",
            //ExistingWorkPolicy.REPLACE,
            //ExistingWorkPolicy.KEEP,
            ExistingWorkPolicy.APPEND,
            myWorkRequest1
        ).then(myWorkRequest3)
            .then(myWorkRequest5)
            .enqueue()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("work request 1  = $myWorkRequest1")
                Text("work request 2  = $myWorkRequest2")
                Text("work request 3  = $myWorkRequest3")
                Text("work request 4  = $myWorkRequest4")
                Text("work request 5  = $myWorkRequest5")
            }
        }
    }
}