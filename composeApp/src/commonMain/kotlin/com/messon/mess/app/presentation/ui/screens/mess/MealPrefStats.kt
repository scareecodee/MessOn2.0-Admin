package com.messon.mess.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.DayChip
import com.messon.mess.app.presentation.ui.component.PrefStatCard
import com.messon.mess.app.presentation.viewmodel.PrefStatVM
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun PrefStatScreen(
    onBackBtnClick:()-> Unit,
    prefStatVM: PrefStatVM
) {
    val uiState by prefStatVM.uiState.collectAsState()
    val weekData by prefStatVM.menu.collectAsState()

    var selectedIndex by remember { mutableStateOf(0) }
    val selectedDay = weekData.getOrNull(selectedIndex)

    LaunchedEffect(Unit) {
        prefStatVM.fetchPrefStat()
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {


        val ( list,statusBarBox,headingRef,backBtnRef) = createRefs()


        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()+5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef){
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ){
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text ="Preference Stats",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.constrainAs(list) {
                top.linkTo(headingRef.bottom, margin = 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

                height = Dimension.fillToConstraints
            }
        ) {
          item {
              LazyRow(
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 12.dp),
                  contentPadding = PaddingValues(horizontal = 20.dp),
                  horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                  itemsIndexed(weekData) { index, day ->
                      DayChip(
                          label = day.day.take(3).uppercase(),
                          isSelected = index == selectedIndex,
                          onClick = { selectedIndex = index }
                      )
                  }
              }
          }

            item{
                selectedDay?.let {
                    PrefStatCard(day = it)
                }            }
        }
    }
}
