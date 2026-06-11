package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.DayDateChip
import com.messon.mess.app.presentation.ui.component.RatingCard
import com.messon.mess.app.presentation.ui.state.RatingState
import com.messon.mess.app.presentation.viewmodel.MealRatingVM
import com.messon.mess.app.theme.MessonTheme
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun MealRatingScreen(
    ratingVM: MealRatingVM,
    onBackBtnClick: () -> Unit = {}
) {
    val fetchState by ratingVM.uiState.collectAsState()
    val dateList by ratingVM.dateList.collectAsState()
    val selectedDate by ratingVM.selectedDate.collectAsState()
    val filteredStats by ratingVM.filteredRating.collectAsState()

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        ratingVM.fetchRating()
    }

    LaunchedEffect(fetchState) {
        when (fetchState) {
            is RatingState.Error -> {
                showToast((fetchState as RatingState.Error).message)
                ratingVM.clearFetchState()
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        val (statusBarBox, headingRef, listRef, backBtnRef, dayDateSelector) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MessonTheme.colors.divider)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                tint = colors.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text = "Meal Rating",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = colors.onSurface,
            modifier = Modifier
                .constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        LazyRow(
            modifier = Modifier
                .constrainAs(dayDateSelector) {
                    top.linkTo(headingRef.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(vertical = 7.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(dateList) { item ->
                DayDateChip(
                    item = item,
                    isSelected = item.date == selectedDate,
                    onClick = {
                        ratingVM.selectDate(item.date)
                    }
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 20.dp),
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(dayDateSelector.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredStats) { rating ->
                RatingCard(
                    mealSlot = rating.mealSlot,
                    avgRating = rating.avgRating,
                    vegRating = rating.vegRating,
                    nonVegRating = rating.nonVegRating,
                    maxRating = rating.maxRating,
                    accentColor = colors.primary,
                    modifier = Modifier,
                )
            }
        }
    }
}