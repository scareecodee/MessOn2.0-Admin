package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ScanStatDto(
    @SerialName("day")
    val day: String,

    @SerialName("date")
    val date: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("scanned_veg_count")
    val scannedVeg: Int,

    @SerialName("scanned_non_veg_count")
    val scannedNonVeg: Int,

    @SerialName("total_veg_count")
    val totalVeg: Int,

    @SerialName("total_non_veg_count")
    val totalNonVeg: Int,

    @SerialName("total_scanned_count")
    val totalScanned: Int
)







