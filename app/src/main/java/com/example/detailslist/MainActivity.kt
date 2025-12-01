package com.example.detailslist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.profile.presentation.notifications.NotificationChannelManager
import com.example.uikit.theme.DetailsListTheme

class MainActivity : ComponentActivity() {
    private val channelManager : NotificationChannelManager by lazy {
        NotificationChannelManager(NotificationManagerCompat.from(this), this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        channelManager.createNotificationChannels()
        enableEdgeToEdge()
        setContent {
            DetailsListTheme {
                MainScreen()
            }
        }
    }
}
