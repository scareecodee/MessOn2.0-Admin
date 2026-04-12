package com.messon.mess.app.data.remote.network

import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CouponGeneratedStudentCountDto
import com.messon.mess.app.data.remote.dto.CouponsScannedDto
import com.messon.mess.app.data.remote.dto.EachSlotAnalyticsDto
import com.messon.mess.app.utils.NetworkError
import io.ktor.client.HttpClient
import com.messon.mess.app.utils.Result
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException


private const val BASE_URL = "http://172.18.9.63:8000/"
class CouponAnalyticsApi(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun fetchTodayAnalytics(
        token:String,
    ): Result<List<EachSlotAnalyticsDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}admin/today/scan-analytics",
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val user=response.body<List<EachSlotAnalyticsDto>>()
                Result.Success(user)
            }
            404-> Result.Error(NetworkError.NOT_FOUND)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun getCouponGeneratedStudentCount(
        token:String,
    ): Result<CouponGeneratedStudentCountDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}admin/coupons/mark-as-used",
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val user=response.body<CouponGeneratedStudentCountDto>()
                Result.Success(user)
            }
            404-> Result.Error(NetworkError.NOT_FOUND)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}