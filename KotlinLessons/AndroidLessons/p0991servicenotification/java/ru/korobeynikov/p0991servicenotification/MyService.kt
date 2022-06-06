package ru.korobeynikov.p0991servicenotification

import android.app.*
import android.content.Intent
import android.os.Build
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
        val notifBuilder=Notification.Builder(this).setContentTitle("Notification's title")
            .setContentText("Notification's text").setSmallIcon(R.mipmap.ic_launcher)
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(MainActivity.FILE_NAME,"somefile")
        val pIntent=PendingIntent.getActivity(this,0,intent,0)
        notifBuilder.setContentIntent(pIntent)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channelId="notif"
            val channel=NotificationChannel(channelId,"Notification channel",NotificationManager.IMPORTANCE_HIGH)
            nm.createNotificationChannel(channel)
            notifBuilder.setChannelId(channelId)
        }
        val notif=notifBuilder.build()
        notif.flags+=Notification.FLAG_AUTO_CANCEL
        nm.notify(1,notif)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}