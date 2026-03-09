package com.messon.mess.app.utils

fun convertDay(day: String): WeekDay {
    return when (day) {
        "Mon" -> WeekDay.MONDAY
        "Tue" -> WeekDay.TUESDAY
        "Wed" -> WeekDay.WEDNESDAY
        "Thu" -> WeekDay.THURSDAY
        "Fri" -> WeekDay.FRIDAY
        "Sat" -> WeekDay.SATURDAY
        else -> WeekDay.SUNDAY
    }
}

fun convertSlot(slot: String): MealSlot {
    return when (slot) {
        "B" -> MealSlot.BREAKFAST
        "L" -> MealSlot.LUNCH
        else -> MealSlot.DINNER
    }
}


fun convertSlotToShort(slot: MealSlot): String {
    return when (slot) {
        MealSlot.BREAKFAST -> "B"
        MealSlot.LUNCH -> "L"
        MealSlot.DINNER -> "D"
    }
}