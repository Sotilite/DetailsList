package com.example.profile.presentation.utils

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
fun timePickerView(
    context: Context,
    currentTime: String = "",
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    try {
        val time = LocalTime.parse(
            currentTime,
            DateTimeFormatter.ofPattern("HH:mm")
        )
        calendar.set(Calendar.HOUR_OF_DAY, time.hour)
        calendar.set(Calendar.MINUTE, time.minute)
    } catch (_: Exception) {
        val now = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE))
    }


    val dialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimeSelected(hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    dialog.setTitle("Выберите время уведомления")
    dialog.show()
}