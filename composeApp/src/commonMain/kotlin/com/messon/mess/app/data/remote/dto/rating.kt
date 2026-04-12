package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RatingDto(
    @SerialName("day")
    val day: String,

    @SerialName("date")
    val date: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("veg_avg")
    val vegRating: Float?,

    @SerialName("non_veg_avg")
    val nonVegRating: Float?,

    @SerialName("max_rating")
    val maxRating: Int,

    @SerialName("total_avg")
    val avgRating: Float?,

    )

