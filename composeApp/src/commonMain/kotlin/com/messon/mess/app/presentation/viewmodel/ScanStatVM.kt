package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.DateItem
import com.messon.mess.app.domain.dataModel.ScanStat
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.ScanStatState
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ScanStatVM(
    private val secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScanStatState>(ScanStatState.Idle)
    val uiState: StateFlow<ScanStatState> = _uiState.asStateFlow()

    private val _scanStat = MutableStateFlow<List<ScanStat>>(emptyList())

    private val _dateList = MutableStateFlow<List<DateItem>>(emptyList())
    val dateList: StateFlow<List<DateItem>> = _dateList.asStateFlow()

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate.asStateFlow()

    val filteredStats: StateFlow<List<ScanStat>> =
        combine(_scanStat, _selectedDate) { stats, selected ->
            if (selected == null) emptyList()
            else stats.filter { it.date.trim() == selected.trim() }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun fetchStats(isToday: Boolean) {

        if (_uiState.value is ScanStatState.Loading) return

        _uiState.value = ScanStatState.Loading

        viewModelScope.launch {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    ScanStatState.Error("Session expired. Please login again.")
                return@launch
            }

            val result = if (isToday) {
                messRepository.fetchTodayScanStats(token)
            } else {
                messRepository.fetchScanStats(token)
            }

            result
                .onSuccess { data ->

                    _scanStat.value = data

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

                    _uiState.value = ScanStatState.Success(data)
                }
                .onError {
                    _uiState.value = ScanStatState.Error(it.toString())
                }
        }
    }

    fun fetchScanStat() = fetchStats(isToday = false)

    fun fetchTodayScanStat() = fetchStats(isToday = true)

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun clearFetchState() {
        _uiState.value = ScanStatState.Idle
    }
}