package com.tes.android.projects.tvshowsapp.presentation.favorite_shows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tes.android.projects.tvshowsapp.core.components.CustomCircularProgressBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tes.android.projects.tvshowsapp.R
import java.util.Timer
import kotlin.concurrent.schedule
import com.tes.android.projects.tvshowsapp.core.components.CustomCircularProgressBar
import com.tes.android.projects.tvshowsapp.core.components.RatingBar
import com.tes.android.projects.tvshowsapp.core.components.ShowItem
import com.tes.android.projects.tvshowsapp.core.navigation.SHOW_DETAIL_SCREEN
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteShowsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val favoriteShows = uiState.favoriteShows

    // Launch a coroutine bound to the scope of the composable
    LaunchedEffect(key1 = viewModel, block = {
        viewModel.onEvent(FavoriteShowsEvent.LoadFavoriteShows)
    })

    if (favoriteShows.isNotEmpty()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBarContent()
            LazyColumn(
                modifier = Modifier.padding(10.dp)
            ) {
                items(uiState.favoriteShows.size) { i ->
                    val show = uiState.favoriteShows[i]

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
                        IconButton(onClick = {
                            viewModel.onEvent(
                                FavoriteShowsEvent.OnDeleteSelected(
                                    show.id
                                )
                            )
                        }
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Delete),
                                contentDescription = "Delete Favorite",
                                tint = White
                            )
                        }
                    }


                    if (i < uiState.favoriteShows.size) {
                        Divider(
                            modifier = Modifier.padding(
                                vertical = 16.dp
                            )
                        )
                    }
                }
            }

        }
    } else if (uiState.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
               CustomCircularProgressBar()
        }
    } else if (!uiState.error.contains("")) {
        Text(
            text = uiState.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            //  .align(Alignment.Center)
        )
        Timer("Show progress bar for about time specified", true).schedule(500) {
            CustomCircularProgressBar()
        }
    } else {
        Text(
            text = "No Data",
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=40.dp,top = 30.dp,)
        )
    }


}

@Composable
fun TopAppBarContent() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.favorite_shows),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
    )
}

@Composable
fun ShowItem(
    show: ShowDetail,
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberAsyncImagePainter(show.image.medium)
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 2.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                painter = imagePainter, contentDescription = "Show",
            )
        }
        Column {
            Text(
                text = show.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = show.type,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = "(${show.runtime})",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(start = 20.dp)) {
                RatingBar(rating = show.rating.average)
                Text(
                    text = "${show.rating.average}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

