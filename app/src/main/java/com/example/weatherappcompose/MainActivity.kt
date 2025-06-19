package com.example.weatherappcompose

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import com.android.volley.Request
import com.example.weatherappcompose.botton_navigation.BottomNavigationLine
import com.example.weatherappcompose.botton_navigation.NavGraph
import com.example.weatherappcompose.data.FavCityModel
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.screens.DialogSearch
import org.json.JSONObject


const val API_KEY = "0f15465bce904906abd134716252705"
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppComposeTheme {
                val navController = rememberNavController()
                val favList = remember {
                    mutableStateOf(listOf<FavCityModel>())
                }
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                val currDay = remember {
                    mutableStateOf(WeatherModel(
                        "",
                        "",
                        "0.0",
                        "",
                        "",
                        "0.0",
                        "0.0",
                        ""
                    )
                    )
                }
                LaunchedEffect(Unit) {
                    getData("Kyiv", this@MainActivity, daysList, currDay)
                }

                if (dialogState.value) {
                    DialogSearch(dialogState, onSubmit = {getData(it, this, daysList, currDay)})
                }
                Scaffold(
                    bottomBar = {
                        BottomNavigationLine(navController = navController)
                    }
                ) { NavGraph(
                    navController = navController,
                    currentDay = currDay,
                    daysList = daysList,
                    onClickSync = { getData("Kyiv", this@MainActivity, daysList, currDay) },
                    onClickSearch = { dialogState.value = true },
                    favList = favList,
                    onClickFav = {
                        val newFavCity = FavCityModel(currDay.value.city, currDay.value.currentTemp, currDay.value.conditionIcon)
                        if (favList.value.none { it.city == newFavCity.city }) {
                            favList.value += newFavCity
                        }
                    },
                    onClickSearchFav = { cityName -> getData(cityName, this@MainActivity, daysList, currDay) },
                )
                }
            }
        }
    }
}

private fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    val url =  "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=" +
            "3" +
            "&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val list = getWeatherByDay(response)
            currentDay.value = list[0]
            daysList.value = list
        },
        {
            Log.d("MyLog", "Error: $it")
        }

    )
    queue.add(sRequest)
}

private fun getWeatherByDay(response: String) : List<WeatherModel> {
    if (response.isEmpty()) return listOf()

    val listWeatherModel = ArrayList<WeatherModel>()

    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        listWeatherModel.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()
                )
        )
    }

    // копия дата класа WeatherModel для 1-го дня (текущего) , перед return чтоб заменить данные по текущей температуре и дате
    listWeatherModel[0] = listWeatherModel[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")
    )

    return listWeatherModel
}

