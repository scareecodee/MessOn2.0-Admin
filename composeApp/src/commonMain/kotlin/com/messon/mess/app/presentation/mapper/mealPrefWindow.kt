package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.FetchPrefTimeDto
import com.messon.mess.app.data.remote.dto.MealPrefTimeDto
import com.messon.mess.app.data.remote.dto.WindowTemplateDto
import com.messon.mess.app.domain.dataModel.FetchPrefTime
import com.messon.mess.app.domain.dataModel.MealPrefTime
import com.messon.mess.app.domain.dataModel.WindowTemplate

fun MealPrefTime.toDto(): MealPrefTimeDto{
    return MealPrefTimeDto(
        startTime = startTime,
        endTime = endTime
    )
}


fun MealPrefTimeDto.toDomain(): MealPrefTime{
    return MealPrefTime(
        startTime = startTime,
        endTime = endTime
    )
}

fun FetchPrefTimeDto.toDomain(): FetchPrefTime{
    return FetchPrefTime(
        startTime = startTime,
        endTime = endTime,
        status = status
    )
}


fun WindowTemplateDto.toDomain(): WindowTemplate{
    return WindowTemplate(
        startDay = startDay,
        endDay = endDay,
        startTime =startTime,
        endTime =endTime
    )
}



fun WindowTemplate.toDto(): WindowTemplateDto{
    return WindowTemplateDto(
        startDay = startDay,
        endDay = endDay,
        startTime =startTime,
        endTime =endTime
    )
}