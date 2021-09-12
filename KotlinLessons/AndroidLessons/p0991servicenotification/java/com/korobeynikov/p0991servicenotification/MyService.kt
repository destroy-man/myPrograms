package com.korobeynikov.p0991servicenotification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.TimeUnit

class MyService : Service() {

    lateinit var nm:NotificationManager

    override fun onCreate() {
        super.onCreate()
        nm=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try{
            TimeUnit.SECONDS.sleep(5)
        }
        catch(e:InterruptedException){
            e.printStackTrace()
        }
        sendNotif()
        return super.onStartCommand(intent, flags, startId)
    }

    fun sendNotif(){
        val nBuilder=Notification.Builder(this).setContentTitle("Notification's title").setContentText("Notification's text")
            .setSmallIcon(R.drawable.ic_launcher_background)
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(MainActivity.FILE_NAME,"somefile")
        val pIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        nBuilder.setContentIntent(pIntent)
        val notif=nBuilder.build()
        notif.flags+=Notification.FLAG_AUTO_CANCEL
        notif.defaults=Notification.DEFAULT_ALL
        notif.number=5
        nm.notify(1,notif)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}