package com.example.weatherappcompose.botton_navigation

import com.example.weatherappcompose.R

sealed class BottomItem(val title: String, val iconId : Int, val route : String ) {
    object FirstScreen : BottomItem("Home", R.drawable.ic_home_weatherapp, "first_screen" )
    object SecondScreen : BottomItem("Saves", R.drawable.ic_location_save_weatherapp, "second_screen" )
}
