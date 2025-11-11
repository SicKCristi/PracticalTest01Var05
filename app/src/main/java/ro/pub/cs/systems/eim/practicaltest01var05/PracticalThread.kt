package ro.pub.cs.systems.eim.practicaltest01var05

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import java.util.Date
import java.util.Random
import kotlin.math.sqrt



class ProcessingThread(private val context: Context, sirbutoane: String, numar_apasari_butoane: Int) :
    Thread() {
    private var isRunning = true

    private val random = Random()

    private val sirul_cu_butoane: String
    private val numar_de_apasari_de_butoane: Int

    init {
        sirul_cu_butoane=sirbutoane
        numar_de_apasari_de_butoane=numar_apasari_butoane
    }

    override fun run() {
        Log.d(
            Constants.PROCESSING_THREAD_TAG,
            "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid()
        )
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!")
    }

    private fun sendMessage() {
        val intent = Intent()
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.size)])
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_EXTRA,
            Date(System.currentTimeMillis()).toString() + " " + sirul_cu_butoane + " " + numar_de_apasari_de_butoane.toString()
        )
        context.sendBroadcast(intent)
    }

    private fun sleep() {
        try {
            sleep(5000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}