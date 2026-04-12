package com.messon.mess.app.domain.dataModel

data class MealPrefTime(
    val startTime: String?,
    val endTime: String?,
)


data class FetchPrefTime(
    val startTime: String?,
    val endTime: String?,
    val status: Boolean
)



data class WindowTemplate(
    val startDay: String?,
    val endDay: String?,
    val startTime: String?,
    val endTime: String?
)