package com.example.detailslist.profile.presentation.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.profile.data.repository.ProfileRepository
import com.example.detailslist.profile.presentation.model.state.EditProfileState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.example.detailslist.profile.presentation.notifications.NotificationsReceiver
import com.example.detailslist.profile.presentation.notifications.NotificationsReceiver.Companion.NOTIFICATION_KEY
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class EditProfileViewModel(
    private val repository: ProfileRepository,
    private val context: Context,
): ViewModel() {
    private val mutableNavigationEvent = MutableSharedFlow<String>()
    val navigationEvent = mutableNavigationEvent.asSharedFlow()

    private val mutableState = MutableEditProfileState()
    val viewState = mutableState as EditProfileState

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.name = it.name
                mutableState.url = it.url
                mutableState.photoUri = it.photoUri.toUri()
                mutableState.favoriteLessonTime = it.favoriteLessonTime
            }
        }
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onUrlChanged(url: String) {
        mutableState.url = url
    }

    fun onBackClicked() {
        viewModelScope.launch {
            mutableNavigationEvent.emit("back")
        }
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let {
            mutableState.photoUri = it
        }
    }

    fun onPermissionClosed() {
        mutableState.isNeedToShowPermission = false
    }

    fun onAvatarClicked() {
        mutableState.isNeedToShowSelect = true
    }

    fun onSelectDismiss() {
        mutableState.isNeedToShowSelect = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFavoriteLessonTimeChange(time: String) {
        mutableState.favoriteLessonTime = time
        checkValidityOfTime(time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkValidityOfTime(time: String) {
        try {
            LocalTime.parse(
                time,
                DateTimeFormatter.ofPattern("HH:mm")
            )
            mutableState.timeErrorMessage = ""
        } catch (_: Exception) {
            mutableState.timeErrorMessage = "Некорректный формат времени"
        }
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onTimeSelected(hour: Int, minute: Int) {
        val timeString = String.format("%02d:%02d", hour, minute)
        onFavoriteLessonTimeChange(timeString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun setNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notifyIntent = Intent(context, NotificationsReceiver::class.java)
        notifyIntent.putExtras(
            Bundle().apply {
                putString(NOTIFICATION_KEY, "Пора на любимую пару ${mutableState.name}")
            }
        )

        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            System.currentTimeMillis().toInt(),
            notifyIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val time = LocalTime.parse(
            mutableState.favoriteLessonTime,
            DateTimeFormatter.ofPattern("HH:mm")
        )
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, 0)

            val now = Calendar.getInstance()
            val currentTime = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
            val scheduledTime = time.hour * 60 + time.minute

//            if (scheduledTime <= currentTime) {
//                add(Calendar.DAY_OF_YEAR, 1)
//            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            notifyPendingIntent
        )
        Log.d("Notification", "Notification scheduled for: $calendar")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun onDoneClicked() {
        viewModelScope.launch {
            repository.setProfile(
                mutableState.photoUri.toString(),
                viewState.name,
                viewState.url,
                viewState.favoriteLessonTime
            )
            setNotification()
            onBackClicked()
        }
    }

    private class MutableEditProfileState : EditProfileState {
        override var photoUri: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var url by mutableStateOf("")
        override  var favoriteLessonTime by mutableStateOf("")
        override  var timeErrorMessage by mutableStateOf("")
        override var isNeedToShowPermission by mutableStateOf(false)
        override var isNeedToShowSelect by mutableStateOf(false)
    }
}