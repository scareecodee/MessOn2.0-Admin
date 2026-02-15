package com.messon.mess.app.domain.auth
import com.messon.mess.app.utils.NetworkError
import com.messon.mess.app.utils.Result

interface GoogleAuth {
    suspend fun getGoogleToken(): Result<String, NetworkError>
}