package com.messon.mess.app.domain.repository
import com.messon.mess.app.data.remote.network.MessApi
import com.messon.mess.app.domain.dataModel.Menu
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
        messId: String,
        menus: List<Menu>
    ): Result<List<Menu>, NetworkError> {

        return when (
            val result =messApi.createMenu(
                token = token,
                messId = messId,
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
        messId: String,
    ): Result<List<Menu>, NetworkError> {

        return when (
            val result =messApi.fetchMessMenu(
                token = token,
                messId = messId,
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

}
