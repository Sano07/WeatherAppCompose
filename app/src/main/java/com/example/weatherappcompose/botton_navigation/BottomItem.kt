package com.example.weatherappcompose.botton_navigation

import com.example.weatherappcompose.R

sealed class BottomItem(val title: String, val iconId : Int, val route : String ) {
    object FirstScreen : BottomItem("Screen 1", R.drawable.ic_home_weatherapp, "first_screen" )
    object Screen2 : BottomItem("Screen 2", R.drawable.ic_location_save_weatherapp, "screen_2" )
}
