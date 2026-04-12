package com.messon.mess.app.domain.dataModel



data class ScanStat(
    val day: String,
    val date: String,
    val mealSlot: String,
    val scannedVeg: Int,
    val scannedNonVeg: Int,
    val totalVeg: Int,
    val totalNonVeg: Int,
    val totalScanned: Int
)


data class DateItem(
    val id: String,
    val day: String,
    val date: String,
    val formatedDate: String,
    val month: String
)