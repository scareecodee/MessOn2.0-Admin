package com.messon.mess.app.data.remote.network

import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.RatingDto
import com.messon.mess.app.utils.NetworkError
import com.messon.mess.app.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

private const val BASE_URL ="https://demo.messon.online/"


class CouponManualGeneration(
    private val httpClient: HttpClient

){
    suspend fun generateCouponManually(
        token:String,
    ): com.messon.mess.app.utils.Result<List<RatingDto>, NetworkError>
    {
        val Log = Logger.withTag("CMP_LOG")

        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}admin/coupons/generate-manually"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
            }
        } catch (e: UnresolvedAddressException){
            return  com.messon.mess.app.utils.Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  com.messon.mess.app.utils.Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  com.messon.mess.app.utils.Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val ratings=response.body<List<RatingDto>>()
                com.messon.mess.app.utils.Result.Success(ratings)
            }
            401-> com.messon.mess.app.utils.Result.Error(NetworkError.UNAUTHORIZED)
            403-> com.messon.mess.app.utils.Result.Error(NetworkError.FORBIDDEN)
            409 -> com.messon.mess.app.utils.Result.Error(NetworkError.CONFLICT)
            408 ->  com.messon.mess.app.utils.Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  com.messon.mess.app.utils.Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  com.messon.mess.app.utils.Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}