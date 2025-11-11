package ro.pub.cs.systems.eim.practicaltest01var05

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.Objects

//import android.support.v4.app.NotificationCompat;
class PracticalTest01Var05Service : Service() {
    var processingThread: ProcessingThread? = null

    override fun onCreate() {
        super.onCreate()

        val CHANNEL_ID = "CEVA VALOARE"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        (Objects.requireNonNull<Any?>(getSystemService(NOTIFICATION_SERVICE)) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            //val notification: Notification? = Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("").build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val sir_de_butoane = intent.getStringExtra(Constants.INPUT1).toString()
        val numar_butoane = intent.getIntExtra(Constants.INPUT2, -1)
        processingThread = ProcessingThread(this, sir_de_butoane, numar_butoane)
        processingThread!!.start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        processingThread!!.stopThread()
    }
}