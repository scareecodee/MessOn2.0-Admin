package com.messon.mess.app.domain.auth



import com.messon.mess.app.domain.dataModel.LoginResponse
import kotlinx.cinterop.*
import platform.Foundation.*
import platform.Security.*

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFBooleanTrue
import qrgenerator.toNSData


actual class AuthSecureStorage {

    private val service = "com.messon.student.app.auth"


    actual suspend fun saveLogin(response: LoginResponse) {
        clear()

        save("access_token", response.accessToken)
        save("token_type", response.tokenType)
        save("email", response.user.mailId)
        save("name", response.user.name)
        save("picture", response.user.profileImg)
        save("id", response.user.id)
        save("expires_at", response.expiresIn)
    }


    actual suspend fun getId(): String? = read("id")
    actual suspend fun getAccessToken(): String? = read("access_token")
    actual suspend fun getEmail(): String? = read("email")
    actual suspend fun getName(): String? = read("name")
    actual suspend fun getPicture(): String? = read("picture")
    actual suspend fun getExpiresAt(): String? = read("expires_at")


    @OptIn(ExperimentalForeignApi::class)
    actual fun clear() {
        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to service
        )
        SecItemDelete(query as CFDictionaryRef)
    }


    actual suspend fun isSessionValid(): Boolean {
        val token = getAccessToken() ?: return false
        val expiresAt = getExpiresAt() ?: return false

        try {
            val normalized = expiresAt.substringBefore('.') + "Z"
            val expiryInstant = Instant.parse(normalized)
            val now = Clock.System.now()

            return if (now < expiryInstant) {
                true
            } else {
                clear()
                false
            }
        } catch (e: Exception) {
            clear()
            return false
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun save(key: String, value: String?) {
        if (value == null) return

        val data = value.encodeToByteArray().toNSData()

        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to service,
            kSecAttrAccount to key,
            kSecValueData to data
        )


        SecItemDelete(query as CFDictionaryRef)
        SecItemAdd(query as CFDictionaryRef, null)
    }


    @OptIn(ExperimentalForeignApi::class)
    private fun read(key: String): String? {
        val query = mapOf(
            kSecClass to kSecClassGenericPassword,
            kSecAttrService to service,
            kSecAttrAccount to key,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne
        )

        memScoped {
            val result = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query as CFDictionaryRef, result.ptr)

            if (status == errSecSuccess) {
                val data = result.value as NSData
                return data.toByteArray().decodeToString()
            }
        }
        return null
    }
}
