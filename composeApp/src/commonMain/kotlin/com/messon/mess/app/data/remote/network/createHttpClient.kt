package com.messon.mess.app.data.remote.network


import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient{
    return  HttpClient(engine){
        install(Logging){
            level= LogLevel.ALL
            logger= Logger.DEFAULT
        }
        install(ContentNegotiation){
            json(
                json= Json {
                    ignoreUnknownKeys=true
                }
            )
        }
    }
}