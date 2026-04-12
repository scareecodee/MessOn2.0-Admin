package com.messon.mess.app.domain.dataModel

data class MealStats(val veg: Int, val nonVeg: Int)

data class EachSlotPrefStat(
    val vegCount: Int?,
    val nonVegCount: Int?,
)

data class EachDayPrefStat(
    val day: String,
    val date: String,
    val breakfastStat: EachSlotPrefStat,
    val lunchStat: EachSlotPrefStat,
    val dinnerStat: EachSlotPrefStat

)
