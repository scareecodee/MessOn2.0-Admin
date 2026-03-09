package com.messon.mess.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.MessRule
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.FetchRulesState
import com.messon.mess.app.presentation.ui.state.SaveRulesState
import com.messon.mess.app.utils.DayStatus
import com.messon.mess.app.utils.WeekDay
import com.messon.mess.app.utils.convertRulesToSchedule
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessRuleVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,
    )
    : ViewModel() {

    private val _schedule =
        MutableStateFlow<Map<WeekDay, DayStatus>>(
            WeekDay.entries.associateWith {
                if (it == WeekDay.SATURDAY || it == WeekDay.SUNDAY)
                    DayStatus.Optional
                else
                    DayStatus.Compulsory
            }
        )

    val schedule: StateFlow<Map<WeekDay, DayStatus>> = _schedule.asStateFlow()

    fun updateDayStatus(day: WeekDay, status: DayStatus) {

        val updated = _schedule.value.toMutableMap()
        updated[day] = status
        _schedule.value = updated
    }

    fun setAllCompulsory() {
        _schedule.value = WeekDay.entries.associateWith { DayStatus.Compulsory }
    }

    fun setAllOptional() {
        _schedule.value = WeekDay.entries.associateWith { DayStatus.Optional }
    }

    fun buildMessRules(): List<MessRule> {

        return _schedule.value.map { (day, status) ->

            MessRule(
                day = day.value,
                isCompulsory = status == DayStatus.Compulsory
            )
        }
    }

    private val _saveRuleState = MutableStateFlow<SaveRulesState>(SaveRulesState.Idle)
    val saveRuleState: StateFlow<SaveRulesState> = _saveRuleState.asStateFlow()

    private val _fetchRuleState = MutableStateFlow<FetchRulesState>(FetchRulesState.Idle)
    val fetchRuleState: StateFlow<FetchRulesState> = _fetchRuleState.asStateFlow()

    fun saveMessRule(rules:List<MessRule>, weekType: String) {
        if(_saveRuleState.value is SaveRulesState.Loading) return
        _saveRuleState.value = SaveRulesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _saveRuleState.value = SaveRulesState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.saveMessRule(
                token = token,
                rules = rules,
                weekType = weekType,
            )
                .onSuccess {
                    _saveRuleState.value = SaveRulesState.Success(it)
                }
                .onError {
                    _saveRuleState.value = SaveRulesState.Error(it.toString())
                }
        }
    }

    fun clearSaveRule(){
        _saveRuleState.value= SaveRulesState.Idle
    }

    fun fetchMessRule(weekType: String) {
        _fetchRuleState.value = FetchRulesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchRuleState.value = FetchRulesState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchMessRule(
                token = token,
                weekType = weekType,
            )
                .onSuccess {
                    _fetchRuleState.value = FetchRulesState.Success(it)
                }
                .onError {
                    _fetchRuleState.value = FetchRulesState.Error(it.toString())
                }
        }
    }

    fun clearFetchRule(){
        _fetchRuleState.value= FetchRulesState.Idle
    }

    fun setInitialSchedule(rules: List<MessRule>) {
        _schedule.value = convertRulesToSchedule(rules)
    }

}