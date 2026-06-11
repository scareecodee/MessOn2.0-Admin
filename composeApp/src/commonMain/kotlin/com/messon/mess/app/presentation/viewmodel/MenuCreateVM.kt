package com.messon.mess.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.mess.app.domain.auth.AuthSecureStorage
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
    private val secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
) : ViewModel() {

    private val _weeklyMenu =
        MutableStateFlow<Map<WeekDay, DayMenu>>(emptyMap())
    val weeklyMenu: StateFlow<Map<WeekDay, DayMenu>> = _weeklyMenu

    private val _uiState = MutableStateFlow<SaveMenuState>(SaveMenuState.Idle)
    val uiState: StateFlow<SaveMenuState> = _uiState.asStateFlow()

    init {
        _weeklyMenu.value = WeekDay.entries.associateWith { DayMenu() }
    }

    fun loadMenu() {
        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken() ?: return@launch

            messRepository.fetchMessMenu(
                token = token,
                weekType = WeekType.CURRENT_WEEK.value
            )
                .onSuccess { menuList ->

                    val grouped = menuList.groupBy { menu ->
                        requireNotNull(
                            WeekDay.entries.find { it.value == menu.day }
                        )
                    }

                    val mappedMenu = WeekDay.entries.associateWith { day ->
                        val menus = grouped[day].orEmpty()
                        var dayMenu = DayMenu()

                        menus.forEach { m ->
                            when (m.mealSlot) {

                                MealSlot.BREAKFAST.value -> {
                                    dayMenu = dayMenu.copy(
                                        breakfast = if (m.mealType == MealType.VEG.value)
                                            dayMenu.breakfast.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.breakfast.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }

                                MealSlot.LUNCH.value -> {
                                    dayMenu = dayMenu.copy(
                                        lunch = if (m.mealType == MealType.VEG.value)
                                            dayMenu.lunch.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.lunch.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }

                                MealSlot.DINNER.value -> {
                                    dayMenu = dayMenu.copy(
                                        dinner = if (m.mealType == MealType.VEG.value)
                                            dayMenu.dinner.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.dinner.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }
                            }
                        }

                        dayMenu
                    }

                    _weeklyMenu.value = mappedMenu
                }
        }
    }

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

    fun saveMenuToDb() {
        if (_uiState.value is SaveMenuState.Loading) return
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
                        Menu(day.value, MealSlot.BREAKFAST.value, MealType.VEG.value, dayMenu.breakfast.veg),
                        Menu(day.value, MealSlot.BREAKFAST.value, MealType.NON_VEG.value, dayMenu.breakfast.nonVeg),
                        Menu(day.value, MealSlot.LUNCH.value, MealType.VEG.value, dayMenu.lunch.veg),
                        Menu(day.value, MealSlot.LUNCH.value, MealType.NON_VEG.value, dayMenu.lunch.nonVeg),
                        Menu(day.value, MealSlot.DINNER.value, MealType.VEG.value, dayMenu.dinner.veg),
                        Menu(day.value, MealSlot.DINNER.value, MealType.NON_VEG.value, dayMenu.dinner.nonVeg)
                    )
                }
            )
                .onSuccess { menuList ->

                    // ✅ Group safely (no fallback bug)
                    val grouped = menuList.groupBy { menu ->
                        requireNotNull(
                            WeekDay.entries.find { it.value == menu.day }
                        ) { "Invalid day: ${menu.day}" }
                    }

                    // ✅ Ensure ALL days are present
                    val mappedMenu = WeekDay.entries.associateWith { day ->

                        val menus = grouped[day].orEmpty()
                        var dayMenu = DayMenu()

                        menus.forEach { m ->
                            when (m.mealSlot) {

                                MealSlot.BREAKFAST.value -> {
                                    dayMenu = dayMenu.copy(
                                        breakfast = if (m.mealType == MealType.VEG.value)
                                            dayMenu.breakfast.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.breakfast.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }

                                MealSlot.LUNCH.value -> {
                                    dayMenu = dayMenu.copy(
                                        lunch = if (m.mealType == MealType.VEG.value)
                                            dayMenu.lunch.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.lunch.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }

                                MealSlot.DINNER.value -> {
                                    dayMenu = dayMenu.copy(
                                        dinner = if (m.mealType == MealType.VEG.value)
                                            dayMenu.dinner.copy(veg = m.dishes.orEmpty())
                                        else
                                            dayMenu.dinner.copy(nonVeg = m.dishes.orEmpty())
                                    )
                                }
                            }
                        }

                        dayMenu
                    }

                    _weeklyMenu.value = mappedMenu

                    _uiState.value = SaveMenuState.Success(menuList)
                }
                .onError {
                    _uiState.value = SaveMenuState.Error(it.toString())
                }
        }
    }
}