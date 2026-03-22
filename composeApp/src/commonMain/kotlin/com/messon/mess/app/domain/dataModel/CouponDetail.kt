package com.messon.mess.app.domain.dataModel


import com.messon.mess.app.data.remote.dto.ErrorDetailDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetail(
    val uid: String?,
    val date: LocalDate?,
    val mealSlot: String?,
    val usedAt:Int?,
    val couponId: String?,
    val messId: String?,
    val collegeId: String?,
    val signature: String?,
    val mealType: String?
) {
    fun isValid(): Boolean =
        !uid.isNullOrBlank() &&
                date.toString().isNotBlank() &&
                !mealSlot.isNullOrBlank() &&
                !couponId.isNullOrBlank() &&
                !signature.isNullOrBlank() &&
                !messId.isNullOrBlank()&&
                !collegeId.isNullOrBlank()&&
                !mealType.isNullOrBlank()
}

data class CouponVerificationRes(
    val studentName: String,
    val rollNo: String,
    val branch: String,
    val mealSlot: String,
    val mealType: String,
    val scannedAt: String,
    val isValid: Boolean,
    val errors:List<ErrorDetail>
)

data class  ErrorDetail(
    val statusCode: Int,
    val message: String
)