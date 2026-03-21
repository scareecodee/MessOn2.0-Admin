package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.MessTimingDto
import com.messon.mess.app.domain.dataModel.MessTiming

fun MessTiming.toDto(): MessTimingDto{
    return MessTimingDto(
        mealSlot = mealSlot,
        startTime = startTime,
        endTime = endTime
    )
}


fun MessTimingDto.toDomain(): MessTiming{
    return MessTiming(
        mealSlot = mealSlot,
        startTime = startTime,
        endTime = endTime
    )
}