package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.RatingDto
import com.messon.mess.app.domain.dataModel.RatingData

fun RatingDto.toDomain(): RatingData{
    return RatingData(
        day = day,
        date = date,
        mealSlot =mealSlot,
        vegRating =vegRating,
        nonVegRating =nonVegRating,
        maxRating =maxRating,
        avgRating = avgRating
    )
}