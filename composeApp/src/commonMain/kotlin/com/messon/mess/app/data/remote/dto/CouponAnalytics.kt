package com.messon.mess.app.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponsScannedDto(

    @SerialName("breakfastCount")
    var breakfastCount: Long,

    @SerialName("lunchCount")
    var lunchCount: Long,

    @SerialName("dinnerCount")
    var dinnerCount: Long,
)


@Serializable
data class CouponGeneratedStudentCountDto(

    @SerialName("total_coupon_count")
    var count: Long,
    )