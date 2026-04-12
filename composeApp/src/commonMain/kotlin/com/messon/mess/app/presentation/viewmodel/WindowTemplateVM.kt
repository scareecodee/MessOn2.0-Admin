package com.messon.mess.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.WindowTemplate
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.WindowTemplateState
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

class WindowTemplateVM(
    private  val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage
)
    : ViewModel() {

    private val _openingTime = MutableStateFlow<LocalTime?>(null)
    val openingTime = _openingTime.asStateFlow()

    private val _closingDay = MutableStateFlow<String?>(null)
    val closingDay = _closingDay.asStateFlow()

    private val _openingDay = MutableStateFlow<String?>(null)
    val openingDay = _openingDay.asStateFlow()

    private val _closingTime = MutableStateFlow<LocalTime?>(null)
    val closingTime = _closingTime.asStateFlow()
    private val _saveTempState= MutableStateFlow<WindowTemplateState>(WindowTemplateState.Idle)

    var saveTempState: StateFlow<WindowTemplateState> = _saveTempState.asStateFlow()

    private val _fetchTempState= MutableStateFlow<WindowTemplateState>(WindowTemplateState.Idle)

    var fetchTempState: StateFlow<WindowTemplateState> = _fetchTempState.asStateFlow()



    fun updateOpeningTime(time: LocalTime?) {
        _openingTime.value = time
    }

    fun updateClosingTime(time: LocalTime?) {
        _closingTime.value = time
    }

    fun updateOpeningDay(day: String?) {
        _openingDay.value = day
    }

    fun updateClosingDay(day: String?) {
        _closingDay.value = day
    }


    fun saveWindowTemplate(
    ) {
        if(_saveTempState.value is WindowTemplateState.Loading) return
        _saveTempState.value = WindowTemplateState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _saveTempState.value = WindowTemplateState.Error("Session expired. Please login again.")
                return@launch
            }

            val openingT = _openingTime.value
            val closingT = _closingTime.value
            val openingD=_openingDay.value
            val closingD=_closingDay.value

            if (openingT == null || closingT == null || openingD==null || closingD==null) {
                _saveTempState.value = WindowTemplateState.Error("fill the form completely")
                return@launch
            }

            messRepository.saveWindowTemplate(
                token = token,
                template = WindowTemplate(
                    startDay =openingD.replaceFirstChar { it.lowercaseChar() },
                    endDay = closingD.replaceFirstChar { it.lowercaseChar() },
                    startTime =openingT.toString(),
                    endTime = closingT.toString()
                ),
            )
                .onSuccess { data ->

                    _saveTempState.value =WindowTemplateState.Success(
                        template = data
                    )

                    _openingDay.value=data.startDay
                    _closingDay.value=data.endDay

                    _openingTime.value = data.startTime?.toLocalTimeSafe()

                    _closingTime.value = data.endTime?.toLocalTimeSafe()
                }
                .onError {
                    _saveTempState.value =WindowTemplateState.Error(it.toString())
                }
        }
    }

    fun clearSaveState(){
        _saveTempState.value= WindowTemplateState.Idle
    }

    fun fetchWindowTemplate(
    ) {
        if(_fetchTempState.value is WindowTemplateState.Loading) return
        _fetchTempState.value = WindowTemplateState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchTempState.value = WindowTemplateState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchWindowTemplate(
                token = token,
            )
                .onSuccess { data ->
                    _openingDay.value=data.startDay
                    _closingDay.value=data.endDay

                    _openingTime.value = data.startTime?.toLocalTimeSafe()

                    _closingTime.value = data.endTime?.toLocalTimeSafe()
                    _fetchTempState.value =WindowTemplateState.Success(
                        template = data
                    )

                }
                .onError {
                    _fetchTempState.value =WindowTemplateState.Error(it.toString())
                }
        }
    }

    fun clearFetchState(){
        _fetchTempState.value= WindowTemplateState.Idle
    }
}