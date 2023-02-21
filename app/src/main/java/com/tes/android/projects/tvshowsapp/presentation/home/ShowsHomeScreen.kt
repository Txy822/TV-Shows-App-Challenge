package com.tes.android.projects.tvshowsapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tes.android.projects.tvshowsapp.core.components.CustomCircularProgressBar
import com.tes.android.projects.tvshowsapp.core.navigation.SHOWLIST_SCREEN
import com.tes.android.projects.tvshowsapp.core.navigation.SHOW_DETAIL_SCREEN
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.R.drawable as AppIcon
import com.tes.android.projects.tvshowsapp.R.string as AppText


@Composable
fun ShowsHomeScreen(
    navController: NavController,
    viewModel: ShowsViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val shows = state.shows

    val topRatedShows = state.shows.sortedByDescending { it.rating.average }

    // Launch a coroutine bound to the scope of the composable, viewModel relaunched
    LaunchedEffect(key1 = viewModel, block = {
        viewModel.onEvent(ShowsEvent.LoadShows)
    })

    if (shows.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            ActionToolbar(
                title = AppText.tvmazshows,
                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                endActionIcon = AppIcon.ic_baseline_settings_24,
                endAction = { }
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))


            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "List of Shows",
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
                Spacer(Modifier.weight(1f))
                Text(text = "More", style = TextStyle(color = Color.Red, fontSize = 16.sp),
                    modifier = Modifier.clickable {
                        navController.navigate(route = SHOWLIST_SCREEN)
                    }
                )
            }

            LazyRow(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                items(shows.size) { i ->
                    val show = shows[i]
                    //FavoriteButton(show = show, viewModel = viewModel)
                    ShowItem_(
                        viewModel = viewModel,
                        show = show,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(route = "$SHOW_DETAIL_SCREEN/${show.name}")
                            }
                    )

                    if (i < shows.size) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 10.dp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Top Rated Shows",
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
                Spacer(Modifier.weight(1f))
                Text(text = "More", style = TextStyle(color = Color.Red, fontSize = 16.sp),
                    modifier = Modifier.clickable {
                        navController.navigate(route = SHOWLIST_SCREEN)
                    }
                )
            }


            LazyRow(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                items(topRatedShows.size) { i ->
                    val show = topRatedShows[i]
                    ShowSmallItem(
                        show = show,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(route = "${SHOW_DETAIL_SCREEN}/${show.name}")
                            }
                    )
                    if (i < topRatedShows.size) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        )
                    }
                }
            }
        }
    }
    else {
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                //  .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
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
fun ShowItem_(
    viewModel: ShowsViewModel,
    show: ShowDetail,
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberAsyncImagePainter(show.image.medium)

    val description = show.status
    val title = show.name
    val rating = show.rating

    ImageCard(
        viewModel = viewModel,
        show = show,
        painter = imagePainter,
        contentDescription = description,
        title = title,
        rating = rating.average,
        modifier = modifier
    )

}

@Composable
fun ShowSmallItem(
    show: ShowDetail,
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberAsyncImagePainter(show.image.medium)
    val name = show.name
    val description = show.type

    SmallImageCard(
        painter = imagePainter,
        rating = show.rating.average,
        name = name,
        contentDescription = description,
        modifier = modifier
    )

}


@Composable
fun ActionToolbar(
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier,
    endAction: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                stringResource(title),
                color = Color.White
            )
        },
        backgroundColor = toolbarColor(),
        modifier = Modifier.padding(10.dp),
        elevation = 4.dp,
        actions = {
            Box(modifier) {
                IconButton(onClick = endAction) {}
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colors.secondary else MaterialTheme.colors.background
}

@Composable
fun ImageCard(
    viewModel: ShowsViewModel,
    show: ShowDetail,
    painter: Painter,
    contentDescription: String,
    title: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(300.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 50f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column() {
                    Text(text = title, style = TextStyle(color = Color.White, fontSize = 20.sp))
                    Row(modifier = Modifier) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Rating",
                            tint = Color.White
                        )
                        Text(
                            text = rating.toString(),
                            style = TextStyle(color = Color.White, fontSize = 20.sp)
                        )
                        FavoriteButton(show = show, viewModel = viewModel, modifier = modifier)
                    }
                }

            }

        }

    }
}


@Composable
fun SmallImageCard(
    painter: Painter,
    name: String,
    contentDescription: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        elevation = 5.dp,
        backgroundColor = Color.Gray
    ) {
        Column(
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Column(
            ) {
                Text(
                    text = name,
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = contentDescription,
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Row(

                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.padding(start = 10.dp)

                    )

                    Text(
                        text = rating.toString(),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }

            }

        }

    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier,
    viewModel: ShowsViewModel = hiltViewModel(),
    show: ShowDetail
) {
    var isFavorite by rememberSaveable(show) { mutableStateOf(show.isFavorite) }

    IconButton(onClick = {
        isFavorite = !isFavorite
        show.isFavorite = isFavorite
        if (isFavorite) {
            viewModel.onEvent(ShowsEvent.OnFavoriteSelected(show))
        } else {
            viewModel.onEvent(ShowsEvent.DeleteFavorite(show.id))
        }
    }) {
        val tintColor = if (isFavorite) Color.Red else Color.White
        Icon(
            painter = rememberVectorPainter(Icons.Default.Favorite),
            contentDescription = "Favorite Icon",
            tint = tintColor,
            modifier = modifier.padding(start = 190.dp, bottom = 30.dp)
        )
    }
}
