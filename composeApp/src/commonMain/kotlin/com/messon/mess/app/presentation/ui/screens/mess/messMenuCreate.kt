package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import co.touchlab.kermit.Logger
import com.messon.mess.app.presentation.ui.component.DaySelector
import com.messon.mess.app.presentation.ui.component.MealSlotTabs
import com.messon.mess.app.presentation.ui.component.MenuInputCard
import com.messon.mess.app.presentation.viewmodel.MenuCreateVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.utils.MealSlot
import com.messon.mess.app.utils.convertDay
import com.messon.mess.app.utils.convertSlot
import com.messon.mess.app.utils.convertSlotToShort
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MenuHeader(
    selectedDay: String,
    onDaySelected: (String) -> Unit,
    modifier: Modifier = Modifier,

) {
    Column(modifier = modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            val (backBtn, titleCol, chefBtn) = createRefs()

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {  }
            ) {
                Icon(
                    Icons.Filled.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = CustomColors.BlackberryCream
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(titleCol) {
                    start.linkTo(backBtn.end)
                    end.linkTo(chefBtn.start)
                    top.linkTo(parent.top)
                }
            ) {
                Text(
                    "Mess Menu",
                    fontSize =18.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = Color.Black
                )
                Text(
                    "Weekly",
                    fontSize =14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    color = CustomColors.BlackberryCream
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(chefBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Icon(
                    Icons.Default.Restaurant,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = CustomColors.RichCerulean
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
    menuCreateVM: MenuCreateVM
) {
    var selectedDay by remember { mutableStateOf("Mon") }
    var activeSlot by remember { mutableStateOf("B") }
    var vegMenu by remember { mutableStateOf("") }
    var nonVegMenu by remember { mutableStateOf("") }
    val weeklyMenu by menuCreateVM.weeklyMenu.collectAsState()
    LaunchedEffect(weeklyMenu) {
        Logger.withTag("mess menu").d { weeklyMenu.toString() }
    }
    LaunchedEffect(selectedDay, activeSlot) {

        val day = convertDay(selectedDay)
        val slot = convertSlot(activeSlot)

        val menu = weeklyMenu[day]

        when (slot) {
            MealSlot.BREAKFAST -> {
                vegMenu = menu?.breakfast?.veg ?: ""
                nonVegMenu = menu?.breakfast?.nonVeg ?: ""
            }

            MealSlot.LUNCH -> {
                vegMenu = menu?.lunch?.veg ?: ""
                nonVegMenu = menu?.lunch?.nonVeg ?: ""
            }

            MealSlot.DINNER -> {
                vegMenu = menu?.dinner?.veg ?: ""
                nonVegMenu = menu?.dinner?.nonVeg ?: ""
            }
        }
    }



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (header, contentCard, saveButton,statusBarBox) = createRefs()

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()+5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(statusBarBox.bottom)
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.radialGradient(
                            0f to CustomColors.HoneyBronze.copy(alpha = 0.15f),
                            1000f to Color.Transparent,
                        )
                    )
            )
            MenuHeader(
                selectedDay = selectedDay,
                onDaySelected = { selectedDay = it }
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(contentCard) {
                    top.linkTo(header.bottom, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                item {
                    MealSlotTabs(
                        activeSlot = activeSlot,
                        onSlotSelected = { activeSlot = it },
                        iconColor = CustomColors.RichCerulean
                    )
                }

                item{
                    Spacer(Modifier.height(24.dp))
                }


                item {
                    MenuInputCard(
                    label = "Vegetarian Selection",
                    value = vegMenu,
                        onValueChange = {
                            vegMenu = it

                            val day = convertDay(selectedDay)
                            val slot = convertSlot(activeSlot)

                            menuCreateVM.updateMenu(
                                day = day,
                                slot = slot,
                                veg = it,
                                nonVeg = nonVegMenu
                            )
                        },
                    icon = Icons.Default.EnergySavingsLeaf,
                    iconBgColor = Color(0xFFDCFCE7),
                    iconTint = Color(0xFF166534),
                    cardBgColor = CustomColors.LightLavender,
                    footerTag = "Fresh"
                ) }


                item {
                    Spacer(Modifier.height(16.dp))

                }

                item {
                    MenuInputCard(
                        label = "Non-Veg Selection",
                        value = nonVegMenu,
                        onValueChange = {
                            nonVegMenu = it

                            val day = convertDay(selectedDay)
                            val slot = convertSlot(activeSlot)

                            menuCreateVM.updateMenu(
                                day = day,
                                slot = slot,
                                veg = it,
                                nonVeg = nonVegMenu
                            )
                        },
                        icon = Icons.Default.Whatshot,
                        iconBgColor = Color(0xFFFFEDD5),
                        iconTint = Color(0xFFEA580C),
                        cardBgColor = CustomColors.Cream,
                        footerTag = "Protein"
                    )
                }


                item {
                    Spacer(Modifier.height(100.dp))
                }
            }
        }

        val Log= Logger.withTag("mess menu")
        Button(
            onClick = {
                val day = convertDay(selectedDay)
                val slot = convertSlot(activeSlot)
                menuCreateVM.updateMenu(
                    day = day,
                    slot = slot,
                    veg =vegMenu ,
                    nonVeg = nonVegMenu
                )
                menuCreateVM.saveMenuToDb()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
                .height(55.dp)
                .constrainAs(saveButton) {
                    bottom.linkTo(parent.bottom)
                },
            colors = ButtonDefaults.buttonColors(containerColor = CustomColors.RichCerulean),
            shape = RoundedCornerShape(32.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Save Mess Menu",
                    fontSize =14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Save Menu",
                    tint=Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}