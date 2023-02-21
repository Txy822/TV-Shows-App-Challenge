package com.tes.android.projects.tvshowsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tes.android.projects.tvshowsapp.presentation.favorite_shows.FavoriteScreen
import com.tes.android.projects.tvshowsapp.presentation.favorite_shows.FavoriteShowsViewModel
import com.tes.android.projects.tvshowsapp.presentation.home.ShowsHomeScreen
import com.tes.android.projects.tvshowsapp.presentation.settings.SettingScreen
//import com.tes.android.projects.tvshowsapp.presentation.favorite.SettingScreen
import com.tes.android.projects.tvshowsapp.presentation.search_shows.ShowListScreen
import com.tes.android.projects.tvshowsapp.presentation.search_shows.SearchShowListingsScreen
import com.tes.android.projects.tvshowsapp.presentation.search_shows.SearchShowsViewModel
import com.tes.android.projects.tvshowsapp.presentation.show_detail.ShowDetailScreen

@Composable
fun NavGraph(navController: NavHostController,
             favoriteShowsViewModel: FavoriteShowsViewModel = hiltViewModel(),
             showListingViewModel: SearchShowsViewModel = hiltViewModel()) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            ShowsHomeScreen(navController)
        }
        composable(BottomNavItem.Search.screen_route) {
            SearchShowListingsScreen(navController, viewModel = showListingViewModel)
        }
        composable(BottomNavItem.Favorite.screen_route) {
            FavoriteScreen(navController=navController, viewModel = favoriteShowsViewModel)
        }
        composable(BottomNavItem.Setting.screen_route) {
            SettingScreen()
        }

        composable("${SHOW_DETAIL_SCREEN}/{name}",
            arguments = listOf(navArgument("name"){type= NavType.StringType})
        ) {
            ShowDetailScreen( navController=navController)
        }
        composable(route= SHOWLIST_SCREEN){
            ShowListScreen(navController = navController)
        }

    }
}