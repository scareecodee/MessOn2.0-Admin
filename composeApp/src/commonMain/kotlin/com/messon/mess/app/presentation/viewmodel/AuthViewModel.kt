package com.messon.mess.app.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.data.remote.dto.LoginRequestDto
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.domain.repository.AuthRepository
import com.messon.mess.app.utils.AuthState
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.launch

import kotlinx.coroutines.*

class AuthViewModel(
    private val secureStorage: AuthSecureStorage,
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = mutableStateOf<AuthState>(AuthState.Idle)
    val uiState: State<AuthState> = _uiState

    fun onGoogleClick(googleAuth: GoogleAuth) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(dispatcher) {
            googleAuth.getGoogleToken()
                .onSuccess { token ->
                    authRepository.login(LoginRequestDto(idToken = token))
                        .onSuccess { loginResponse ->
                            secureStorage.saveLogin(response = loginResponse)
                            _uiState.value = AuthState.Success(loginResponse.user)
                        }
                        .onError {
                            _uiState.value = AuthState.Error(it.toString())
                        }
                }
                .onError {
                    _uiState.value = AuthState.Error(it.toString())
                }
        }
    }

    fun clear() {
        viewModelScope.cancel()
    }
}
