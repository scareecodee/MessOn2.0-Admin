package com.messon.mess.app.domain.auth


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CustomCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.withTimeout
import com.messon.mess.app.utils.Result
import com.messon.mess.app.utils.NetworkError

class GoogleAuthAndroid(
    private val serverClientId: String,
    private val context: Context,
) : GoogleAuth {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun getGoogleToken():Result<String, NetworkError>  {
        if (context !is Activity) {
            throw IllegalArgumentException("Context must be an Activity")
        }
        return try {
            Log.d("GoogleAuth", "Before getCredential $serverClientId")


            val option = GetGoogleIdOption.Builder()
                .setServerClientId(serverClientId)
                .setFilterByAuthorizedAccounts(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(option)
                .build()
            val result = withTimeout(15000L) { // 15 seconds
                credentialManager.getCredential(context, request)
            }

            Log.d("GoogleAuth", "After getCredential")


            val credential = result.credential
            Log.d("GoogleAuth", "Credential class = ${credential::class}")
            Log.d("GoogleAuth", "Credential type = ${credential.type}")

            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val token = GoogleIdTokenCredential
                    .createFrom(credential.data)
                    .idToken
                Log.d("my token",token)
                Result.Success(token)
            } else {
                Result.Error(NetworkError.INVALID_CREDENTIAL)
            }

        } catch (e: NoCredentialException) {
            Result.Error(NetworkError.UNAUTHORIZED)

        } catch (e: GetCredentialException) {
            Log.d("GoogleAuth",e.message.toString())
            Result.Error(NetworkError.SERVER_ERROR)

        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }
}