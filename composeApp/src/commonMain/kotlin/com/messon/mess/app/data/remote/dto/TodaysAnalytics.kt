package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EachSlotAnalyticsDto(

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("veg_count")
    val vegCount: Int,

    @SerialName("non_veg_count")
    val nonVegCount: Int,

    @SerialName("total_count")
    val totalCount: Int,
)






