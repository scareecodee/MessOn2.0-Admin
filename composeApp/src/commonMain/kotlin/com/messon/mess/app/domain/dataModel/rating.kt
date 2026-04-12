package com.messon.mess.app.domain.dataModel

data class RatingData(
    val day: String,
    val date: String,
    val mealSlot: String,
    val vegRating: Float?,
    val nonVegRating: Float?,
    val maxRating: Int,
    val avgRating: Float?
)