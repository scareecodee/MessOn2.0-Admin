package com.messon.mess.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.repository.CouponVerificationRepo
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.launch
import co.touchlab.kermit.Logger
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.domain.dataModel.CouponVerificationRes
import com.messon.mess.app.presentation.mapper.toDto
import com.messon.mess.app.presentation.ui.state.CouponVerificationState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CouponViewModel(
    private val secureStorage: AuthSecureStorage,
    private val couponVerificationRepo: CouponVerificationRepo,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val log = Logger.withTag("CMP_LOG")

    private val _couponDetail=MutableStateFlow<CouponVerificationRes?>(null)
    val couponDetail=_couponDetail.asStateFlow()

    fun updateCouponDetail(couponDetail: CouponVerificationRes){
        _couponDetail.value=couponDetail
    }

    fun clearCouponDetail(){
        _couponDetail.value=null
    }
    private val _couponVerificationState = MutableStateFlow<CouponVerificationState>(CouponVerificationState.Idle)
    val couponVerificationState: StateFlow<CouponVerificationState> =_couponVerificationState.asStateFlow()

    fun verifyCoupon(
        couponDetail: CouponDetail
    ) {
        _couponVerificationState.value = CouponVerificationState.Loading

        viewModelScope.launch(dispatcher) {

            val token = secureStorage.getAccessToken()
            log.d(tag = "token from disk", message = { token.toString() })

            if (token.isNullOrBlank()) {
                _couponVerificationState.value =
                    CouponVerificationState.Error("Session expired. Please login again.")
                return@launch
            }

            couponVerificationRepo.verifyCoupon(
                token=token,
                couponDetailDto =couponDetail.toDto()
            )
                .onSuccess {
                    _couponVerificationState.value = CouponVerificationState.Success(it)
                    updateCouponDetail(it)
                }
                .onError {
                    _couponVerificationState.value = CouponVerificationState.Error(it.toString())
                }
        }
    }


    fun clearVerificationError(){
        _couponVerificationState.value= CouponVerificationState.Idle
    }

}