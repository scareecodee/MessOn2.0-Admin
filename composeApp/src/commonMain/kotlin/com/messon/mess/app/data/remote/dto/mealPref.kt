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