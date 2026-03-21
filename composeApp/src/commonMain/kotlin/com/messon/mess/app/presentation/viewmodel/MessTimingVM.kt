package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.MessTiming
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.screens.mess.getBySlot
import com.messon.mess.app.presentation.ui.state.MessTimingState
import com.messon.mess.app.utils.MealSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import com.messon.mess.app.utils.toLocalTimeSafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.LocalTime

class MessTimingVM(
    private  val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage
)
    : ViewModel() {

    private val _selectedSlot=MutableStateFlow<String?>(null)

    fun setMealSlot(mealSlot: String){
        _selectedSlot.value=mealSlot
    }
    private val _openingTime = MutableStateFlow<LocalTime?>(null)
    val openingTime = _openingTime.asStateFlow()

    private val _closingTime = MutableStateFlow<LocalTime?>(null)
    val closingTime = _closingTime.asStateFlow()
    
    private val _saveState= MutableStateFlow<MessTimingState>(MessTimingState.Idle)

    var saveState: StateFlow<MessTimingState> = _saveState.asStateFlow()

    private val _fetchState= MutableStateFlow<MessTimingState>(MessTimingState.Idle)

    var fetchState: StateFlow<MessTimingState> = _fetchState.asStateFlow()

    fun updateOpening(time: LocalTime) {
        _openingTime.value = time
    }

    fun updateClosing(time: LocalTime) {
        _closingTime.value = time
    }

    fun saveMessTiming(
    ) {
        if(_saveState.value is MessTimingState.Loading) return
        _saveState.value = MessTimingState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _saveState.value = MessTimingState.Error("Session expired. Please login again.")
                return@launch
            }

            val slot = _selectedSlot.value
            val opening = _openingTime.value
            val closing = _closingTime.value

            if (slot == null || opening == null || closing == null) {
                _saveState.value = MessTimingState.Error("Fill all fields")
                return@launch
            }


            messRepository.saveMessTiming(
                token = token,
                messTiming =listOf(
                    MessTiming(
                        mealSlot = slot,
                        startTime = opening.toString(),
                        endTime = closing.toString()
                    )
                ),
            )
                .onSuccess { data ->
                    val item = data.firstOrNull()

                    if (item == null) {
                        _saveState.value = MessTimingState.Error("No data returned")
                        return@onSuccess
                    }
                    _saveState.value = MessTimingState.Success(data)

                    _openingTime.value = item.startTime?.toLocalTimeSafe()
                    _closingTime.value = item.endTime?.toLocalTimeSafe()
                }
                .onError {
                    _saveState.value = MessTimingState.Error(it.toString())
                }
        }
    }

    fun fetchMessTiming(
    ) {
        if(_fetchState.value is MessTimingState.Loading) return
        _fetchState.value = MessTimingState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchState.value = MessTimingState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchMessTiming(
                token = token,
            )
                .onSuccess { data ->
                    _fetchState.value = MessTimingState.Success(data)

                    val slot = _selectedSlot.value ?: return@onSuccess

                    val item = data.getBySlot(slot)

                    _openingTime.value = item?.startTime?.toLocalTimeSafe()
                    _closingTime.value = item?.endTime?.toLocalTimeSafe()
                }
                .onError {
                    _fetchState.value = MessTimingState.Error(it.toString())
                }
        }
    }

    fun clearFetchState(){
      _fetchState.value= MessTimingState.Idle
    }

    fun clearSaveState(){
        _saveState.value= MessTimingState.Idle

    }


}