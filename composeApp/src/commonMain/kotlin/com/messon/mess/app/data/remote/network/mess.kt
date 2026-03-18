package com.messon.mess.app.data.remote.network
import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CreateMenuReqDto
import com.messon.mess.app.data.remote.dto.MenuResDto
import com.messon.mess.app.data.remote.dto.MessRuleDto
import com.messon.mess.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import com.messon.mess.app.utils.Result
import io.ktor.client.request.get
import io.ktor.client.request.patch

private const val BASE_URL = "http://172.18.9.63:8000/"

class MessApi(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun createMenu(
        token:String,
        weekType: String,
        menus:List<CreateMenuReqDto>
    ): Result<List<MenuResDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {menus.toString()})

        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}messes/menus/${weekType}"
            ) {

                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(menus)
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
                val mess=response.body<List<MenuResDto>>()
                Result.Success(mess)
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


    suspend fun fetchMessMenu(
        token:String,
        weekType: String,
    ): Result<List<MenuResDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching mess menu"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/menus/${weekType}"
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

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<List<MenuResDto>>()
                Log.d(tag = "ktor", message = {mess.toString()})
                Result.Success(mess)
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

    suspend fun saveMessRule(
        weekType: String,
        token:String,
        rules:List<MessRuleDto>
    ): Result<List<MessRuleDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {rules.toString()})

        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}messes/day-policy/${weekType}"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(rules)
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
                val mess=response.body<List<MessRuleDto>>()
                Result.Success(mess)
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

    suspend fun fetchMessRule(
        weekType: String,
        token:String,
    ): Result<List<MessRuleDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching mess rule"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/messes/day-policy/${weekType}"
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

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<List<MessRuleDto>>()
                Result.Success(mess)
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