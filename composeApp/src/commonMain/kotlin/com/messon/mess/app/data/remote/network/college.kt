package com.messon.mess.app.data.remote.network


import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CollegeDto
import com.messon.mess.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import io.ktor.client.request.get
import com.messon.mess.app.utils.Result

private const val BASE_URL ="https://demo.messon.online/"
class CollegeApi(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun fetchColleges(
    ): com.messon.mess.app.utils.Result<List<CollegeDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch-colleges"
            ) {
                contentType(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val user=response.body<List<CollegeDto>>()
                Result.Success(user)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}