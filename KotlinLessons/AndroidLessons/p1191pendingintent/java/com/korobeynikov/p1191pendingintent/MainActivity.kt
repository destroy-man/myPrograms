package com.korobeynikov.p1191pendingintent

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var nm:NotificationManager
    lateinit var am:AlarmManager
    lateinit var intent1:Intent
    lateinit var intent2:Intent
    lateinit var pIntent1:PendingIntent
    lateinit var pIntent2:PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nm=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        am=getSystemService(ALARM_SERVICE) as AlarmManager
    }

    fun onClick1(view:View){
        intent1=createIntent("action","extra 1")
        pIntent1=PendingIntent.getBroadcast(this,0,intent1,0)
        intent2=createIntent("action","extra 2")
        pIntent2= PendingIntent.getBroadcast(this,0,intent2,0)
        Log.d("qwe","start")
        am.set(AlarmManager.RTC,System.currentTimeMillis()+2000,pIntent1)
        am.set(AlarmManager.RTC,System.currentTimeMillis()+4000,pIntent2)
    }

    fun onClick2(view:View){
        am.cancel(pIntent2)
    }

    fun createIntent(action:String,extra:String):Intent{
        val intent=Intent(this,Receiver::class.java)
        intent.action=action
        intent.putExtra("extra",extra)
        return intent
    }

    fun compare(){
        Log.d(LOG_TAG,"intent1 = intent2: "+intent1.filterEquals(intent2))
        Log.d(LOG_TAG,"pIntent1 = pIntent2: "+pIntent1.equals(pIntent2))
    }

    companion object {
        val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL"
        fun createNotificationChannel(context: Context) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                val name="name"
                val description="description"
                val importance=NotificationManager.IMPORTANCE_DEFAULT
                val channel=NotificationChannel(NOTIFICATION_CHANNEL_ID,name,importance)
                channel.description=description
                val notificationManager=context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun sendNotif(id:Int,pIntent:PendingIntent){
        createNotificationChannel(this)
        val builder=NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setContentTitle("Title "+id)
            .setContentText("Content "+id).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setContentIntent(pIntent).build()
        nm.notify(id,builder)
    }
}