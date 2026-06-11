package com.messon.mess.app.data.remote.network
import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CouponDetailDto
import com.messon.mess.app.data.remote.dto.CouponVerificationResDto
import com.messon.mess.app.utils.NetworkError
import io.ktor.client.HttpClient
import com.messon.mess.app.utils.Result
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException


private const val BASE_URL ="https://messon2-0-backend-testingv1.onrender.com/"



class CouponVerification(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun verifyCoupon(
        token:String,
        couponDetailDto: CouponDetailDto
    ): Result<CouponVerificationResDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"verifying coupon"})

        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}coupons/scan",
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(
                    couponDetailDto
                )
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

        val a=response.body<String>()
        Log.d(tag = "Ktor", message = {a})

        return when(response.status.value){
            in 200..299->{
                val user=response.body<CouponVerificationResDto>()
                Result.Success(user)
            }
            400->Result.Error(NetworkError.ALREADY_USED_COUPON)
            404-> Result.Error(NetworkError.NOT_FOUND)
            403->Result.Error(NetworkError.FORBIDDEN)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}