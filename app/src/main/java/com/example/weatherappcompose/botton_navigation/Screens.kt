package com.example.weatherappcompose.botton_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.screens.MainCardTemp
import com.example.weatherappcompose.screens.TabLayout

@Composable
fun FirstScreen(
    currentDay: MutableState<WeatherModel>,
    daysList: MutableState<List<WeatherModel>>,
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit
) {
    Column {
        MainCardTemp(currentDay, onClickSync, onClickSearch)
        TabLayout(daysList, currentDay)
    }

}

@Composable
fun Screen2() {
    Text(text = "Screen 2")
}