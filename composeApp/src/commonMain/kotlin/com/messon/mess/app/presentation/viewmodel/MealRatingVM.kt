package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.DateItem
import com.messon.mess.app.domain.dataModel.RatingData
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.RatingState
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class MealRatingVM(
    private val secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RatingState>(RatingState.Idle)
    val uiState: StateFlow<RatingState> = _uiState.asStateFlow()

    private val _ratings = MutableStateFlow<List<RatingData>>(emptyList())

    private val _dateList = MutableStateFlow<List<DateItem>>(emptyList())
    val dateList: StateFlow<List<DateItem>> = _dateList.asStateFlow()

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate.asStateFlow()

    val filteredRating: StateFlow<List<RatingData>> =
        combine(_ratings, _selectedDate) { ratings, selected ->
            if (selected == null) emptyList()
            else ratings.filter { it.date.trim() == selected.trim() }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

     fun fetchRating() {

        if (_uiState.value is RatingState.Loading) return

        _uiState.value = RatingState.Loading

        viewModelScope.launch {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    RatingState.Error("Session expired. Please login again.")
                return@launch
            }

            val result =
                messRepository.fetchRatings(token)

            result
                .onSuccess { data ->

                    _ratings.value = data

                    val dates = data
                        .distinctBy { it.date }
                        .mapIndexed { index, stat ->

                            val localDate = LocalDate.parse(stat.date)

                            DateItem(
                                id = index.toString(),
                                day = stat.day.replaceFirstChar { it.uppercase() },
                                date = stat.date,
                                month = localDate.month.name.take(3),
                                formatedDate = localDate.dayOfMonth.toString()
                            )
                        }

                    _dateList.value = dates

                    if (dates.isNotEmpty()) {
                        _selectedDate.value = dates.first().date
                    }

                    _uiState.value = RatingState.Success(data)
                }
                .onError {
                    _uiState.value = RatingState.Error(it.toString())
                }
        }
    }


    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun clearFetchState() {
        _uiState.value = RatingState.Idle
    }
}