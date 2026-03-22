package com.messon.mess.app.domain.repository

import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CouponDetailDto
import com.messon.mess.app.data.remote.network.CouponVerification
import com.messon.mess.app.domain.dataModel.CouponVerificationRes
import com.messon.mess.app.presentation.mapper.toDomain
import com.messon.mess.app.utils.NetworkError
import com.messon.mess.app.utils.Result

class CouponVerificationRepo (
    private val couponVerification: CouponVerification
){
    val Log= Logger.withTag("CMP_TOAST")

    suspend fun verifyCoupon(
        token:String,
        couponDetailDto: CouponDetailDto
    ): Result<CouponVerificationRes, NetworkError> {
        val dtoResult=couponVerification.verifyCoupon(token = token, couponDetailDto = couponDetailDto)
        Log.d(tag ="coupon veri", message = {couponDetailDto.toString()})
        return when (dtoResult) {
            is Result.Success -> {
                val domain= dtoResult.data.toDomain()
               Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }
}