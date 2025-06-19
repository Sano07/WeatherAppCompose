package com.example.weatherappcompose.botton_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.weatherappcompose.data.FavCityModel
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.screens.MainCardTemp
import com.example.weatherappcompose.screens.SavedLocation
import com.example.weatherappcompose.screens.TabLayout

@Composable
fun FirstScreen(
    currentDay: MutableState<WeatherModel>,
    daysList: MutableState<List<WeatherModel>>,
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFav: () -> Unit
) {
    Column {
        MainCardTemp(currentDay, onClickSync, onClickSearch, onClickFav)
        TabLayout(daysList, currentDay)
    }

}

@Composable
fun SecondScreen(
    favList: MutableState<List<FavCityModel>>,
    onClickSearchFav: (String) -> Unit,
) {
    Column {
        SavedLocation(favList, onClickSearchFav)
    }
}