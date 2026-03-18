package com.messon.mess.app.utils

import kotlinx.datetime.LocalDateTime

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