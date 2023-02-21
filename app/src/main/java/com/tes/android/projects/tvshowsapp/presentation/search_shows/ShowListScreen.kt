package com.tes.android.projects.tvshowsapp.presentation.search_shows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tes.android.projects.tvshowsapp.core.components.CustomCircularProgressBar
import com.tes.android.projects.tvshowsapp.core.components.ShowItem
import com.tes.android.projects.tvshowsapp.core.navigation.SHOW_DETAIL_SCREEN


@Composable
fun ShowListScreen(
    navController: NavController,
    viewModel: SearchShowsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val shows = uiState.shows


    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isRefreshing
    )

    // Launch a coroutine bound to the scope of the composable, viewModel relaunched
    LaunchedEffect(key1 = viewModel, block = {
        viewModel.onEvent(SearchShowsEvent.LoadShows)
    })

    if (shows.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            //TopAppBarContent()
            TopAppBarContentForListOfShows(
                navController
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(SearchShowsEvent.Refresh)
                }
            ) {
                LazyColumn(
                    //modifier = Modifier.fillMaxSize()
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(shows.size) { i ->
                        val show = shows[i]

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            ShowItem(
                                show = show,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(route = "$SHOW_DETAIL_SCREEN/${show.name}")
                                    }
                                    .padding(8.dp)
                            )
                            FavoriteButton(show = show, viewModel = viewModel)
                        }
                        if (i < shows.size) {
                            Divider(
                                modifier = Modifier.padding(
                                    vertical = 16.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    } else {
        if (uiState.error.isNotBlank()) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                //  .align(Alignment.Center)
            )
        }
        if (uiState.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomCircularProgressBar()
            }
        }
    }
}


@Composable
fun TopAppBarContentForListOfShows(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "List of Shows",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                )
            }
        }
    )
}
