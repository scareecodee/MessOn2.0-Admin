package com.messon.mess.app.domain.repository
import com.messon.mess.app.data.remote.dto.MealPrefTimeDto
import com.messon.mess.app.data.remote.network.MessApi
import com.messon.mess.app.domain.dataModel.FetchPrefTime
import com.messon.mess.app.domain.dataModel.MealPrefTime
import com.messon.mess.app.domain.dataModel.Menu
import com.messon.mess.app.domain.dataModel.MessRule
import com.messon.mess.app.domain.dataModel.MessTiming
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


    suspend fun saveMealPrefWindow(
        token: String,
        weekType: String,
        mealPrefTime: MealPrefTime
    ): Result<FetchPrefTime, NetworkError> {

        return when (
            val result =messApi.saveMealPrefWindow(
                token = token,
                weekType = weekType,
                mealPrefTime = mealPrefTime.toDto(),
            )
        ) {
            is Result.Success -> {

             val dto=result.data

                Result.Success(
                    data =dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchMealPrefWindow(
        token: String,
        weekType: String,
    ): Result<FetchPrefTime, NetworkError> {

        return when (
            val result =messApi.fetchPrefWindow(
                token = token,
                weekType = weekType,
            )
        ) {
            is Result.Success -> {

                val dto=result.data

                Result.Success(
                    data =dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun saveMessTiming(
        token: String,
        messTiming: List<MessTiming>
    ): Result<List<MessTiming>, NetworkError> {

        return when (
            val result =messApi.saveMessTiming(
                token = token,
                messTiming = messTiming.map {
                    it.toDto()
                },
            )
        ) {
            is Result.Success -> {

                val dto=result.data

                Result.Success(
                    data =dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun fetchMessTiming(
        token: String,
    ): Result<List<MessTiming>, NetworkError> {

        return when (
            val result =messApi.fetchMessTiming(
                token = token
            )
        ) {
            is Result.Success -> {

                val dto=result.data

                Result.Success(
                    data =dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }
}
