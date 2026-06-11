package com.messon.mess.app.presentation.ui.screens.college

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.CollegeCard
import com.messon.mess.app.presentation.ui.component.SearchBar
import com.messon.mess.app.presentation.ui.state.FetchCollegesState
import com.messon.mess.app.presentation.viewmodel.CollegeScreenVM
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeListScreen(
    onCardClick: () -> Unit,
    onBackClick: () -> Unit,
    collegeScreenVM: CollegeScreenVM
) {
    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()
    val uiState by collegeScreenVM.uiState.collectAsState()
    val isRefreshing = uiState is FetchCollegesState.Loading

    LaunchedEffect(Unit) {
        collegeScreenVM.fetchColleges()
    }

    val colleges = (uiState as? FetchCollegesState.Success)?.colleges ?: emptyList()
    val filteredColleges = colleges.filter {
        it.collegeName.contains(query, ignoreCase = true)
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FetchCollegesState.Error -> {
                showToast(message = "Unable to load")
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (statusBarBox, headingRef, listRef, backBtnRef, searchBarRef) = createRefs()

        // Adaptive Status Bar Area
        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background) // Replaced divider color to prevent harsh visual cutoff
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onBackground // Dynamic high-contrast icon tint
            )
        }

        Text(
            text = "Available Colleges",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = MaterialTheme.colorScheme.onBackground, // Dynamic text color
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        SearchBar(
            placeholderText = "Search your college here",
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(backBtnRef.bottom, 10.dp)
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end, 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        PullToRefreshBox(
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    containerColor = MaterialTheme.colorScheme.primary,
                    color = MaterialTheme.colorScheme.onPrimary,
                    isRefreshing = isRefreshing,
                    state = pullToRefreshState
                )
            },
            isRefreshing = isRefreshing,
            onRefresh = {
                collegeScreenVM.fetchColleges()
            },
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(listRef) {
                    top.linkTo(searchBarRef.bottom, 16.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState) {
                    is FetchCollegesState.Success -> {
                        items(filteredColleges, key = { it.collegeId }) { college ->
                            CollegeCard(
                                collegeName = college.collegeName,
                                onClick = {
                                    collegeScreenVM.selectCollege(college)
                                    onCardClick()
                                }
                            )
                        }
                    }

                    is FetchCollegesState.Error -> {
                        item {
                            Spacer(Modifier.height(200.dp))
                            Text(
                                text = "Unable to load. Pull to refresh",
                                color = MaterialTheme.colorScheme.onSurfaceVariant, // Adaptive dark-mode safe reading color
                                fontSize = 14.sp
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}