package com.example.profile.presentation.notifications

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
//import com.example.detailslist.MainActivity
import com.example.profile.R

class NotificationsReceiver : BroadcastReceiver() {
    @SuppressLint("FullScreenIntentPolicy")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java,
        ) as NotificationManager

        val text = intent.getStringExtra(NOTIFICATION_KEY).orEmpty()
        notificationManager.sendNotification(text, context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun NotificationManager.sendNotification(
        messageBody: String,
        applicationContext: Context,
    ) {
//        val intent = Intent(applicationContext, MainActivity::class.java).apply {
//            Intent.setFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(
//            applicationContext,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )

        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        )

        builder.setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Сработало напоминание")
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)

        notify(1, builder.build())
    }

    companion object {
        const val NOTIFICATION_KEY: String = "NOTIFICATION_MESSAGE"
    }
}