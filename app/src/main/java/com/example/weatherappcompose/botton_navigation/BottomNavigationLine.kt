package com.example.weatherappcompose.botton_navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherappcompose.data.WeatherModel

@Composable
fun BottomNavigationLine(navController: NavController) {
    val listItems = listOf(BottomItem.FirstScreen, BottomItem.SecondScreen)

    BottomNavigation(backgroundColor = Color.White) {
      val backStackEntry by navController.currentBackStackEntryAsState()
        val currRoute = backStackEntry?.destination?.route    // проверка какой скрин открыт в текущий момент

        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconId), contentDescription = "")
                },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Gray
            )
        }
    }
}