package com.messon.mess.app.domain.repository
import com.messon.mess.app.data.remote.network.MessApi
import com.messon.mess.app.domain.dataModel.Menu
import com.messon.mess.app.domain.dataModel.MessRule
import com.messon.mess.app.presentation.mapper.toDomain
import com.messon.mess.app.presentation.mapper.toDto
import com.messon.mess.app.utils.NetworkError
import com.messon.mess.app.utils.Result

class MessRepository(
    private val messApi: MessApi
) {
    suspend fun saveMenuToDb(
        token: String,
        weekType: String,
        menus: List<Menu>
    ): Result<List<Menu>, NetworkError> {

        return when (
            val result =messApi.createMenu(
                token = token,
                weekType =weekType,
                menus = menus.map { menu->
                    menu.toDto()
                }
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }
    suspend fun fetchMessMenu(
        token: String,
        weekType: String,
    ): Result<List<Menu>, NetworkError> {

        return when (
            val result =messApi.fetchMessMenu(
                token = token,
                weekType =weekType,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun saveMessRule(
        token: String,
        rules:List<MessRule>,
        weekType: String
    ): Result<List<MessRule>, NetworkError> {

        return when (
            val result =messApi.saveMessRule(
                token = token,
                weekType = weekType,
                rules = rules.map {
                    it.toDto()
                },
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchMessRule(
        token: String,
        weekType: String
    ): Result<List<MessRule>, NetworkError> {

        return when (
            val result =messApi.fetchMessRule(
                token = token,
                weekType = weekType,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

}
