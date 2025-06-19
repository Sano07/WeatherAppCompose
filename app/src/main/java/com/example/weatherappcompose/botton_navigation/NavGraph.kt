package com.example.weatherappcompose.botton_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherappcompose.data.FavCityModel
import com.example.weatherappcompose.data.WeatherModel

@Composable
fun NavGraph(
    navController: NavHostController,
    currentDay: MutableState<WeatherModel>,
    daysList: MutableState<List<WeatherModel>>,
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFav: () -> Unit,
    onClickSearchFav: (String) -> Unit,
    favList: MutableState<List<FavCityModel>>
) {
    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(currentDay = currentDay, daysList = daysList, onClickSync = onClickSync, onClickSearch = onClickSearch, onClickFav = onClickFav )
        }
        composable("second_screen") {
            SecondScreen(favList = favList, onClickSearchFav = onClickSearchFav)
        }
    }
}