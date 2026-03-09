package com.messon.mess.app.domain.repository
import com.messon.mess.app.data.remote.network.Auth
import com.messon.mess.app.domain.dataModel.LoginRequest
import com.messon.mess.app.domain.dataModel.LoginResponse
import com.messon.mess.app.presentation.mapper.toDomain
import com.messon.mess.app.presentation.mapper.toDto
import com.messon.mess.app.utils.NetworkError
import com.messon.mess.app.utils.Result
class AuthRepository (
    private val auth: Auth
){
    suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse, NetworkError>{
        val dtoResult=auth.login(loginRequest.toDto())

        return when (dtoResult) {
            is Result.Success -> {
                val domain= dtoResult.data.toDomain()
                Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }
}