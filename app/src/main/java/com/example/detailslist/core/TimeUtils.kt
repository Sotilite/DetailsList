package com.example.detailslist.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun tryParseServerDate(dateTime: String?): LocalDateTime? {
    return try {
        val zonedDateTime = java.time.ZonedDateTime.parse(dateTime)
        zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    } catch (e: DateTimeParseException) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime?.orNow(): LocalDateTime {
    return this ?: LocalDateTime.now()
}