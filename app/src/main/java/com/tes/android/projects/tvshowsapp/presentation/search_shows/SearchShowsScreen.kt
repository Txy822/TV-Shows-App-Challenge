package com.tes.android.projects.tvshowsapp.presentation.search_shows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tes.android.projects.tvshowsapp.core.components.CustomCircularProgressBar
import com.tes.android.projects.tvshowsapp.core.components.ShowItem
import com.tes.android.projects.tvshowsapp.core.components.TopAppBarContent
import com.tes.android.projects.tvshowsapp.core.navigation.SHOW_DETAIL_SCREEN
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail


@Composable
fun SearchShowListingsScreen(
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

            TopAppBarContent()

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.onEvent(
                        SearchShowsEvent.OnSearchQueryChange(it)
                    )
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true,
                label = { Text("Search") }
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(SearchShowsEvent.Refresh)
                }
            ) {
                LazyColumn(
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
                                        navController.navigate(route = "${SHOW_DETAIL_SCREEN}/${show.name}")
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
    }
    else {
        if (uiState.error.isNotBlank()) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom=50.dp)
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
fun FavoriteButton(
    viewModel: SearchShowsViewModel = hiltViewModel(),
    show: ShowDetail
) {
    var isFavorite by rememberSaveable(show) { mutableStateOf(show.isFavorite) }

    IconButton(onClick = {
        isFavorite = !isFavorite
        show.isFavorite = isFavorite
        if (isFavorite) {
            viewModel.onEvent(SearchShowsEvent.OnFavoriteSelected(show))
        } else {
            viewModel.onEvent(SearchShowsEvent.DeleteFavorite(show.id))
        }
    }) {
        val tintColor = if (isFavorite) Red else White
        Icon(
            painter = rememberVectorPainter(Icons.Default.Favorite),
            contentDescription = null,
            tint = tintColor
        )
    }
}




