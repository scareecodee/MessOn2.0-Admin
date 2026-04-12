package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.EachDayPrefStat
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.PrefStatState
import com.messon.mess.app.utils.WeekType
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrefStatVM(
    private val  secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<PrefStatState>(PrefStatState.Idle)
    val uiState: StateFlow<PrefStatState> =_uiState.asStateFlow()

    private val _prefStat = MutableStateFlow<List<EachDayPrefStat>>(emptyList())
    val menu: StateFlow<List<EachDayPrefStat>> = _prefStat.asStateFlow()


    fun fetchPrefStat(
    ) {
        if(_uiState.value is PrefStatState.Loading) return
        _uiState.value = PrefStatState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    PrefStatState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchPrefStats(
                token = token,
                weekType = WeekType.CURRENT_WEEK.value,
            )
                .onSuccess {
                    _prefStat.value = it
                    _uiState.value = PrefStatState.Success(it)
                }
                .onError {
                    _uiState.value = PrefStatState.Error(it.toString())
                }
        }
    }

}