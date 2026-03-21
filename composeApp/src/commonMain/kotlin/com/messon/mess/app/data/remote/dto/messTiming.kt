package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessTimingDto(
    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("start_time")
    val startTime: String?,

    @SerialName("end_time")
    val endTime: String?,
)