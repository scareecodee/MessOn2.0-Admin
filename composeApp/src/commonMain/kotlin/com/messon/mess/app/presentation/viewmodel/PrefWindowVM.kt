package com.messon.mess.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.MealPrefTime
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.PrefWindowState
import com.messon.mess.app.utils.WeekType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import com.messon.mess.app.utils.toISTString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class PrefWindowVM(
  private  val messRepository: MessRepository,
  private val secureStorage: AuthSecureStorage
)
    : ViewModel() {

    private val _openingTime = MutableStateFlow<LocalDateTime?>(null)
    val openingTime = _openingTime.asStateFlow()

    private val _closingTime = MutableStateFlow<LocalDateTime?>(null)
    val closingTime = _closingTime.asStateFlow()

    private val _windowStatus=MutableStateFlow<Boolean>(false)
    val windowStatus=_windowStatus.asStateFlow()

    private val _saveState= MutableStateFlow<PrefWindowState>(PrefWindowState.Idle)

    var saveState: StateFlow<PrefWindowState> = _saveState.asStateFlow()

    private val _fetchState= MutableStateFlow<PrefWindowState>(PrefWindowState.Idle)

    var fetchState: StateFlow<PrefWindowState> = _fetchState.asStateFlow()

    fun updateOpening(time: LocalDateTime) {
        _openingTime.value = time
    }

    fun updateClosing(time: LocalDateTime) {
        _closingTime.value = time
    }
    
    fun savePrefWindow(
        weekType: WeekType,
    ) {
        if(_saveState.value is PrefWindowState.Loading) return
        _saveState.value = PrefWindowState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _saveState.value = PrefWindowState.Error("Session expired. Please login again.")
                return@launch
            }

            val opening = _openingTime.value
            val closing = _closingTime.value

            if (opening == null || closing == null) {
                _saveState.value = PrefWindowState.Error("Select both times")
                return@launch
            }

            messRepository.saveMealPrefWindow(
                token = token,
                weekType = weekType.value,
                mealPrefTime = MealPrefTime(
                    startTime = opening.toISTString(),
                    endTime = closing.toISTString()
                )
            )
                .onSuccess { data ->

                    _saveState.value = PrefWindowState.Success(data)

                    _openingTime.value = data.startTime
                        ?.replace(" ", "T")
                        ?.toLocalDateTime()

                    _closingTime.value = data.endTime
                        ?.replace(" ", "T")
                        ?.toLocalDateTime()
                    _windowStatus.value=data.status

                }
                .onError {
                    _saveState.value = PrefWindowState.Error(it.toString())
                }
        }
    }

    fun fetchPrefWindow(
        weekType: WeekType,
    ) {
        if(_fetchState.value is PrefWindowState.Loading) return
        _fetchState.value = PrefWindowState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchState.value = PrefWindowState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchMealPrefWindow(
                token = token,
                weekType = weekType.value,
            )
                .onSuccess { data ->

                    _fetchState.value = PrefWindowState.Success(data)

                    _openingTime.value = data.startTime
                        ?.replace(" ", "T")
                        ?.toLocalDateTime()

                    _closingTime.value = data.endTime
                        ?.replace(" ", "T")
                        ?.toLocalDateTime()
                    _windowStatus.value=data.status
                }
                .onError {
                    _fetchState.value = PrefWindowState.Error(it.toString())
                }
        }
    }
    
    
}