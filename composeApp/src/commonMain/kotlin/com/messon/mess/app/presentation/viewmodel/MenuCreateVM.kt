package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.domain.dataModel.DayMenu
import com.messon.mess.app.domain.dataModel.Menu
import com.messon.mess.app.domain.dataModel.MenuItem
import com.messon.mess.app.domain.repository.MessRepository
import com.messon.mess.app.presentation.ui.state.SaveMenuState
import com.messon.mess.app.utils.MealSlot
import com.messon.mess.app.utils.MealType
import com.messon.mess.app.utils.WeekDay
import com.messon.mess.app.utils.WeekType
import com.messon.mess.app.utils.onError
import com.messon.mess.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuCreateVM(
   private val  secureStorage: AuthSecureStorage,
   private val messRepository: MessRepository
) : ViewModel() {

    private val _weeklyMenu =
        MutableStateFlow<Map<WeekDay, DayMenu>>(emptyMap())

    val weeklyMenu: StateFlow<Map<WeekDay, DayMenu>> = _weeklyMenu

    init {
        val initialMenu = WeekDay.entries.associateWith {
            DayMenu()
        }
        _weeklyMenu.value = initialMenu
    }

    private val _uiState = MutableStateFlow<SaveMenuState>(SaveMenuState.Idle)
    val uiState: StateFlow<SaveMenuState> =_uiState.asStateFlow()
    fun updateMenu(
        day: WeekDay,
        slot: MealSlot,
        veg: String,
        nonVeg: String
    ) {
        val currentMenu = _weeklyMenu.value.toMutableMap()

        val dayMenu = currentMenu[day] ?: DayMenu()

        val updatedDayMenu = when (slot) {

            MealSlot.BREAKFAST -> dayMenu.copy(
                breakfast = MenuItem(veg, nonVeg)
            )

            MealSlot.LUNCH -> dayMenu.copy(
                lunch = MenuItem(veg, nonVeg)
            )

            MealSlot.DINNER -> dayMenu.copy(
                dinner = MenuItem(veg, nonVeg)
            )
        }

        currentMenu[day] = updatedDayMenu

        _weeklyMenu.value = currentMenu
    }

    fun saveMenuToDb(
    ) {
        if(_uiState.value is SaveMenuState.Loading) return
        _uiState.value = SaveMenuState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    SaveMenuState.Error("Session expired. Please login again.")
                return@launch
            }

           messRepository.saveMenuToDb(
               token = token,
               weekType = WeekType.CURRENT_WEEK.value,
               menus = _weeklyMenu.value.flatMap { (day, dayMenu) ->

                   listOf(
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.BREAKFAST.value,
                           mealType = MealType.VEG.value,
                           dishes = dayMenu.breakfast.veg
                       ),
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.BREAKFAST.value,
                           mealType = MealType.NON_VEG.value,
                           dishes = dayMenu.breakfast.nonVeg
                       ),
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.LUNCH.value,
                           mealType = MealType.VEG.value,
                           dishes = dayMenu.lunch.veg
                       ),
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.LUNCH.value,
                           mealType = MealType.NON_VEG.value,
                           dishes = dayMenu.lunch.nonVeg
                       ),
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.DINNER.value,
                           mealType = MealType.VEG.value,
                           dishes = dayMenu.dinner.veg
                       ),
                       Menu(
                           day = day.value,
                           mealSlot = MealSlot.DINNER.value,
                           mealType = MealType.NON_VEG.value,
                           dishes = dayMenu.dinner.nonVeg
                       )
                   )

               }
           )
                .onSuccess {
                    _uiState.value = SaveMenuState.Success(it)
                }
                .onError {
                    _uiState.value = SaveMenuState.Error(it.toString())
                }
        }
    }

}