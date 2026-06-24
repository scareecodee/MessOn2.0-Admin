package com.messon.mess.app.data.remote.network
import co.touchlab.kermit.Logger
import com.messon.mess.app.data.remote.dto.CreateMenuReqDto
import com.messon.mess.app.data.remote.dto.EachDayPrefStatDto
import com.messon.mess.app.data.remote.dto.FetchPrefTimeDto
import com.messon.mess.app.data.remote.dto.MealPrefTimeDto
import com.messon.mess.app.data.remote.dto.MenuResDto
import com.messon.mess.app.data.remote.dto.MessRuleDto
import com.messon.mess.app.data.remote.dto.MessTimingDto
import com.messon.mess.app.data.remote.dto.RatingDto
import com.messon.mess.app.data.remote.dto.ScanStatDto
import com.messon.mess.app.data.remote.dto.WindowTemplateDto
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
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText

private const val BASE_URL ="https://demo.messon.online/"

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
        Log.d(tag = "Ktor", message = {response.body<String>()})

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
        Log.d(tag = "Ktor", message = { response.bodyAsText() })

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

    suspend fun saveMealPrefWindow(
        weekType: String,
        token:String,
        mealPrefTime: MealPrefTimeDto
    ): Result<FetchPrefTimeDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"saving pref window"})

        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}mess/create/submission-window/${weekType}"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(mealPrefTime)
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
                val mess=response.body<FetchPrefTimeDto>()
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


    suspend fun fetchPrefWindow(
        weekType: String,
        token:String,
    ): Result<FetchPrefTimeDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"saving pref window"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/mess/submission-window/${weekType}"
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
                val mess=response.body<FetchPrefTimeDto>()
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

    suspend fun saveMessTiming(
        token:String,
        messTiming:List<MessTimingDto>
    ): Result<List<MessTimingDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"saving pref window"})

        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}create/mess/meal-slot-expiry"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(messTiming)
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
                val mess=response.body<List<MessTimingDto>>()
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


    suspend fun fetchMessTiming(
        token:String,
    ): Result<List<MessTimingDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"saving pref window"})


        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/mess/meal-slot-expiry"
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
                val mess=response.body<List<MessTimingDto>>()
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



    suspend fun saveWindowTemplate(
        token:String,
        template: WindowTemplateDto
    ): Result<WindowTemplateDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"saving window template"})
        Log.d(tag = "Ktor", message = {template.toString()})

        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}create/mess/submission-window/template"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(template)
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
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<WindowTemplateDto>()
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

    suspend fun fetchWindowTemplate(
        token:String,
    ): Result<WindowTemplateDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching window template"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/mess/submission-window/template"
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
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<WindowTemplateDto>()
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

    suspend fun fetchPrefStats(
        token:String,
        weekType: String
    ): Result<List<EachDayPrefStatDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching pref stat template"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/meal-preference-analytics/${weekType}"
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
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<List<EachDayPrefStatDto>>()
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

    suspend fun fetchScanStats(
        days: Int=14,
        token:String,
    ): Result<List<ScanStatDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching scan stat"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}mess/scan-analytics"
            ) {
                contentType(ContentType.Application.Json)
                parameter("days", days)
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
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<List<ScanStatDto>>()
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

    suspend fun fetchRatings(
        days: Int=7,
        token:String,
    ): Result<List<RatingDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching ratings "})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}rating-analytics"
            ) {
                contentType(ContentType.Application.Json)
                parameter("days", days)
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
        Log.d(tag = "Ktor", message = {response.bodyAsText()})

        return when(response.status.value){
            in 200..299->{
                val ratings=response.body<List<RatingDto>>()
                Result.Success(ratings)
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