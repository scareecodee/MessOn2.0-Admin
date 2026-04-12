package com.messon.mess.app.utils

import com.messon.mess.app.domain.dataModel.MessTiming
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toHumanReadable(): String {
    val months = listOf(
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    )

    val hour12 = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }

    val ampm = if (hour < 12) "AM" else "PM"

    val minuteFormatted = minute.toString().padStart(2, '0')

    return "$dayOfMonth ${months[monthNumber - 1]} $year, $hour12:$minuteFormatted $ampm"
}


fun LocalTime.to12HourTime(includeSeconds: Boolean = false): String {
    var hour = this.hour
    val minute = this.minute
    val second = this.second

    val ampm = if (hour < 12) "AM" else "PM"

    hour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }

    val hourStr = if (hour < 10) "0$hour" else "$hour"
    val minuteStr = if (minute < 10) "0$minute" else "$minute"

    return if (includeSeconds) {
        val secondStr = if (second < 10) "0$second" else "$second"
        "$hourStr:$minuteStr:$secondStr $ampm"
    } else {
        "$hourStr:$minuteStr $ampm"
    }
}


fun LocalDateTime.toISTString(): String {
    val istZone = TimeZone.of("Asia/Kolkata")
    val instant = this.toInstant(istZone)
    val istDateTime = instant.toLocalDateTime(istZone)

    return istDateTime.toString() + "+05:30"
}


fun String.toLocalTimeSafe(): LocalTime {
    return try {
        LocalTime.parse(this)
    } catch (e: Exception) {
        LocalTime.parse("$this:00")
    }
}


fun MessTiming.toDisplayTime(): String {
    val start = startTime?.toLocalTimeSafe()?.to12HourTime()
    val end = endTime?.toLocalTimeSafe()?.to12HourTime()

    return if (start != null && end != null) {
        "$start - $end"
    } else {
        "--"
    }
}