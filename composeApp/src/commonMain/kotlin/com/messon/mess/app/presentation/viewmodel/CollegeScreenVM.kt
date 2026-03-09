package com.messon.mess.app.presentation.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.dataModel.College
import com.messon.mess.app.domain.repository.CollegeRepository
import com.messon.mess.app.presentation.ui.state.FetchCollegesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.messon.mess.app.utils.Result

class CollegeScreenVM(
    val collegeRepository: CollegeRepository
)
    : ViewModel() {

    private val _uiState = MutableStateFlow<FetchCollegesState>(FetchCollegesState.Idle)

    var uiState: StateFlow<FetchCollegesState> = _uiState.asStateFlow()

    fun selectCollege(college: College) {
        collegeRepository.selectCollege(college)
    }

    fun clearSelectedMess(){
        clearSelectedMess()
    }

    fun fetchColleges(
    ){
        if(_uiState.value is FetchCollegesState.Loading) return
        viewModelScope.launch {

            _uiState.value = FetchCollegesState.Loading
            when (val result =collegeRepository.fetchColleges()) {

                is Result.Success -> {


                    _uiState.value = FetchCollegesState.Success(
                        colleges = result.data
                    )

                }

                is Result.Error -> {

                    _uiState.value = FetchCollegesState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun clearCollegeFetchState(){
        _uiState.value= FetchCollegesState.Idle
    }

}