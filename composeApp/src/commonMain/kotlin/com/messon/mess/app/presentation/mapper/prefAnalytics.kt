package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.EachDayPrefStatDto
import com.messon.mess.app.data.remote.dto.EachSlotPrefStatDto
import com.messon.mess.app.domain.dataModel.EachDayPrefStat
import com.messon.mess.app.domain.dataModel.EachSlotPrefStat

fun EachDayPrefStatDto.toDomain(): EachDayPrefStat{

    return EachDayPrefStat(
        day =day,
        date = date,
        breakfastStat =breakfastStat.toDomain(),
        lunchStat =lunchStat.toDomain(),
        dinnerStat = dinnerStat.toDomain()
    )
}

fun EachSlotPrefStatDto.toDomain(): EachSlotPrefStat{
    return EachSlotPrefStat(
        vegCount = vegCount,
        nonVegCount = nonVegCount
    )
}