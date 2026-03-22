package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.CouponDetailDto
import com.messon.mess.app.data.remote.dto.CouponVerificationResDto
import com.messon.mess.app.data.remote.dto.ErrorDetailDto
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.domain.dataModel.CouponVerificationRes
import com.messon.mess.app.domain.dataModel.ErrorDetail
import kotlinx.datetime.LocalDate


fun CouponDetailDto.toDomain(): CouponDetail{
    return CouponDetail(
        uid = uid,
        date = date,
        mealSlot = mealSlot,
        usedAt = usedAt,
        couponId = couponId,
        signature = signature,
        messId = messId,
        collegeId = collegeId,
        mealType = mealType,
    )
}


fun CouponDetail.toDto(): CouponDetailDto{

    return CouponDetailDto(
        uid = uid ?: "",
        couponId = couponId ?: "",
        date = date ?: LocalDate(1970, 1, 1),
        mealSlot = mealSlot ?: "",
        usedAt = usedAt,
        signature = signature ?: "",
        messId = messId?:"",
        collegeId = collegeId?:"",
        mealType = mealType?:"",
    )
}


fun CouponVerificationResDto.toDomain(): CouponVerificationRes{
    return CouponVerificationRes(
        studentName = studentName,
        rollNo = rollNo,
        branch = branch,
        mealSlot = mealSlot,
        mealType = mealType,
        scannedAt = scannedAt,
        isValid = isValid,
        errors =errors.map {
            it.toDomain()
        }
    )
}



fun ErrorDetailDto.toDomain(): ErrorDetail{
    return ErrorDetail(
        statusCode = statusCode,
        message =message
    )
}
