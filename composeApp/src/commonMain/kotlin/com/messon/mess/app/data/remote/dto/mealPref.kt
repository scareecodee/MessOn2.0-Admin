package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPrefTimeDto(
    @SerialName("start_time")
    val startTime: String?,

    @SerialName("end_time")
    val endTime: String?,
)


@Serializable
data class FetchPrefTimeDto(

    @SerialName("is_active")
    val status: Boolean,

    @SerialName("start_time")
    val startTime: String?,

    @SerialName("end_time")
    val endTime: String?,

)


@Serializable
data class WindowTemplateDto(

    @SerialName("start_day")
    val startDay: String?,

    @SerialName("end_day")
    val endDay: String?,

    @SerialName("start_time")
    val startTime: String?,

    @SerialName("end_time")
    val endTime: String?

)


@Serializable
data class EachSlotPrefStatDto(

    @SerialName("veg_count")
    val vegCount: Int?,

    @SerialName("non_veg_count")
    val nonVegCount: Int?,
)

@Serializable
data class EachDayPrefStatDto(
    @SerialName("day")
    val day: String,

    @SerialName("date")
    val date: String,

    @SerialName("breakfast")
    val breakfastStat: EachSlotPrefStatDto,

    @SerialName("lunch")
    val lunchStat: EachSlotPrefStatDto,

    @SerialName("dinner")
    val dinnerStat: EachSlotPrefStatDto

)


