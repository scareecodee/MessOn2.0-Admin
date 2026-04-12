package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.ScanStatDto
import com.messon.mess.app.domain.dataModel.ScanStat

fun ScanStatDto.toDomain(): ScanStat{
    return ScanStat(
        day = day,
        date = date,
        mealSlot =mealSlot,
        scannedVeg=scannedVeg,
        scannedNonVeg =scannedNonVeg,
        totalVeg = totalVeg,
        totalNonVeg =totalNonVeg,
        totalScanned =totalScanned
    )
}