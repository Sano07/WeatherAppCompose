package com.example.weatherappcompose.data

import androidx.compose.runtime.Composable

data class WeatherModel(
    val city: String,
    val time: String,
    val currentTemp: String,
    val conditionText: String,
    val conditionIcon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
)
