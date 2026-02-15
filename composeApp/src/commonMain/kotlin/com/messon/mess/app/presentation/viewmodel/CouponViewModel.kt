package com.messon.mess.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.repository.CouponVerificationRepo
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.launch
import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CouponDetailDto
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.presentation.mapper.toDto
import com.messon.mess.app.utils.CouponVerificationState
import com.messon.mess.app.utils.MarkCouponAsUsedState
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

    private val _couponVerificationState = MutableStateFlow<CouponVerificationState>(CouponVerificationState.Idle)
    val couponVerificationState: StateFlow<CouponVerificationState> =_couponVerificationState.asStateFlow()

    private val _markCouponAsUsed = MutableStateFlow<MarkCouponAsUsedState>(MarkCouponAsUsedState.Idle)
    val markCouponAsUsed: StateFlow<MarkCouponAsUsedState> =_markCouponAsUsed.asStateFlow()
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
                }
                .onError {
                    _couponVerificationState.value = CouponVerificationState.Error(it.toString())
                }
        }
    }

    fun markCouponAsUsed(
        couponDetail: CouponDetail
    ) {
        _markCouponAsUsed.value = MarkCouponAsUsedState.Loading

        viewModelScope.launch(dispatcher) {

            val token = secureStorage.getAccessToken()
            log.d(tag = "token from disk", message = { token.toString() })

            if (token.isNullOrBlank()) {
                _markCouponAsUsed.value =
                    MarkCouponAsUsedState.Error("Session expired. Please login again.")
                return@launch
            }

           couponVerificationRepo.markCouponAsUsed(token = token, couponDetailDto = couponDetail.toDto())
                .onSuccess {
                   _markCouponAsUsed.value = MarkCouponAsUsedState.Success(it)
                }
                .onError {
                    _markCouponAsUsed.value = MarkCouponAsUsedState.Error(it.toString())
                }
        }
    }

    fun clearVerificationError(){
        _couponVerificationState.value= CouponVerificationState.Idle
    }

    fun clearMarkAsUsedError(){
        _markCouponAsUsed.value= MarkCouponAsUsedState.Idle
    }
}