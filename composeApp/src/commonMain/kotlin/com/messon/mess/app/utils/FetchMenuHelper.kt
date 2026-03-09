package com.messon.mess.app.utils

import com.messon.mess.app.domain.dataModel.Menu

fun getDish(
    menu: List<Menu>,
    day: String,
    slot: String,
    diet: Int
): String {

    val mealType =
        if (diet == 0) MealType.VEG.value
        else MealType.NON_VEG.value

    return menu.firstOrNull {
        it.day.lowercase().startsWith(day.lowercase()) &&
                it.mealSlot.equals(slot, ignoreCase = true) &&
                it.mealType.lowercase() == mealType.lowercase()
    }?.dishes ?: "Not available"
}