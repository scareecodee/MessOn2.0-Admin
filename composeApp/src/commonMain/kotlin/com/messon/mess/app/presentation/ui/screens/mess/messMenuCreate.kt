package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.DaySelector
import com.messon.mess.app.presentation.ui.component.MealSlotTabs
import com.messon.mess.app.presentation.ui.component.MenuInputCard
import com.messon.mess.app.presentation.ui.state.SaveMenuState
import com.messon.mess.app.presentation.viewmodel.MenuCreateVM
import com.messon.mess.app.theme.MessonTheme
import com.messon.mess.app.utils.MealSlot
import com.messon.mess.app.utils.convertDay
import com.messon.mess.app.utils.convertSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MenuHeader(
    selectedDay: String,
    onDaySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            val (backBtn, titleCol) = createRefs()

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = colors.surface,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onBackClick() }
            ) {
                Icon(
                    Icons.Filled.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = colors.onSurface
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(titleCol) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
            ) {
                Text(
                    "Mess Menu",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = colors.onSurface
                )
                Text(
                    "Weekly",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    color = colors.onSurfaceVariant
                )
            }
        }
        DaySelector(
            modifier = Modifier,
            selectedDay = selectedDay,
            onDaySelected = onDaySelected
        )
    }
}

@Composable
fun CreateMenuScreen(
    menuCreateVM: MenuCreateVM,
    onBackClick: () -> Unit
) {
    var selectedDay by remember { mutableStateOf("Mon") }
    var activeSlot by remember { mutableStateOf("B") }
    var vegMenu by remember { mutableStateOf("") }
    var nonVegMenu by remember { mutableStateOf("") }
    val uiState by menuCreateVM.uiState.collectAsState()
    val weeklyMenu by menuCreateVM.weeklyMenu.collectAsState()

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) { menuCreateVM.loadMenu() }

    LaunchedEffect(selectedDay, activeSlot, weeklyMenu) {
        val day = convertDay(selectedDay)
        val slot = convertSlot(activeSlot)
        val menu = weeklyMenu[day]

        val newVeg = when (slot) {
            MealSlot.BREAKFAST -> menu?.breakfast?.veg
            MealSlot.LUNCH -> menu?.lunch?.veg
            MealSlot.DINNER -> menu?.dinner?.veg
        } ?: ""

        val newNonVeg = when (slot) {
            MealSlot.BREAKFAST -> menu?.breakfast?.nonVeg
            MealSlot.LUNCH -> menu?.lunch?.nonVeg
            MealSlot.DINNER -> menu?.dinner?.nonVeg
        } ?: ""

        if (vegMenu != newVeg) vegMenu = newVeg
        if (nonVegMenu != newNonVeg) nonVegMenu = newNonVeg
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(colors.background)
    ) {
        val (header, contentCard, saveButton, statusBarBox) = createRefs()

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background( MessonTheme.colors.divider)
                .constrainAs(statusBarBox) { top.linkTo(parent.top); end.linkTo(parent.end) }
        )

        Box(modifier = Modifier.fillMaxWidth().constrainAs(header) { top.linkTo(statusBarBox.bottom) }) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp).background(
                    Brush.radialGradient(
                        0f to colors.primaryContainer.copy(alpha = 0.3f),
                        1000f to Color.Transparent,
                    )
                )
            )
            MenuHeader(
                selectedDay = selectedDay,
                onDaySelected = { selectedDay = it },
                onBackClick = onBackClick
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth().constrainAs(contentCard) {
                top.linkTo(header.bottom, margin = (-20).dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            },
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = colors.background,
            shadowElevation = 8.dp
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                item {
                    MealSlotTabs(
                        activeSlot = activeSlot,
                        onSlotSelected = { activeSlot = it },
                        iconColor = colors.primary
                    )
                }
                item { Spacer(Modifier.height(24.dp)) }
                item {
                    MenuInputCard(
                        label = "Vegetarian Selection",
                        value = vegMenu,
                        onValueChange = {
                            vegMenu = it
                            menuCreateVM.updateMenu(convertDay(selectedDay), convertSlot(activeSlot), it, nonVegMenu)
                        },
                        icon = Icons.Default.EnergySavingsLeaf,
                        iconBgColor = MessonTheme.colors.success.copy(alpha = 0.1f),
                        iconTint = MessonTheme.colors.success,
                        cardBgColor = colors.surface,
                        footerTag = "Fresh"
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    MenuInputCard(
                        label = "Non-Veg Selection",
                        value = nonVegMenu,
                        onValueChange = {
                            nonVegMenu = it
                            menuCreateVM.updateMenu(convertDay(selectedDay), convertSlot(activeSlot), vegMenu, it)
                        },
                        icon = Icons.Default.Whatshot,
                        iconBgColor = MessonTheme.colors.success.copy(alpha = 0.1f),
                        iconTint = MessonTheme.colors.success,
                        cardBgColor = colors.surface,
                        footerTag = "Protein"
                    )
                }
                item { Spacer(Modifier.height(100.dp)) }
            }
        }

        Button(
            enabled = uiState !is SaveMenuState.Loading,
            onClick = { menuCreateVM.saveMenuToDb() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
                .height(55.dp)
                .constrainAs(saveButton) { bottom.linkTo(parent.bottom) },
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
            shape = RoundedCornerShape(32.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Save Mess Menu", fontSize = 14.sp, fontFamily = FontFamily(Font(Res.font.publicsansmedium)))
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.CheckCircle, contentDescription = "Save", tint = Color.White, modifier = Modifier.size(25.dp))
            }
        }
    }
}