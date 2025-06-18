package com.example.weatherappcompose.screens

import android.annotation.SuppressLint
import android.app.LauncherActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.R
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.ui.theme.CardColor
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import com.google.accompanist.pager.ExperimentalPagerApi
import org.json.JSONArray
import org.json.JSONObject


// основная , верхняя карточка в приложении
@Composable
fun MainCardTemp(currentDay : MutableState<WeatherModel>, onClickSync: () -> Unit, onClickSearch: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(5.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = CardColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.5.dp
            ),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = currentDay.value.time,
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.conditionIcon}",
                        contentDescription = "im_weather",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 12.dp, end = 12.dp),
                    )
                }
                Text(
                    text = currentDay.value.city,
                    style = TextStyle(fontSize = 40.sp),
                    color = Color.White
                )
                Text(
                    text = if(currentDay.value.currentTemp.isNotEmpty())
                        currentDay.value.currentTemp.toFloat().toInt().toString() + "°"
                        else "${currentDay.value.maxTemp.toFloat().toInt()}°/${currentDay.value.minTemp.toFloat().toInt()}°",
                    style = TextStyle(fontSize = 70.sp),
                    color = Color.White
                )
                Text(
                    text = currentDay.value.conditionText,
                    style = TextStyle(fontSize = 25.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                    IconButton(
                        onClick = {
                            onClickSearch.invoke()
                        }
                    )
                    {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "im_search",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${currentDay.value.maxTemp.toFloat().toInt()}°/${currentDay.value.minTemp.toFloat().toInt()}°",
                        style = TextStyle(fontSize = 25.sp),
                        color = Color.White
                    )
                    IconButton(
                        onClick = {
                            onClickSync.invoke()
                        }
                    )
                    {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.ic_refresh),
                            contentDescription = "im_refresh",
                            tint = Color.White
                        )

                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState(
        pageCount = { tabList.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            backgroundColor = CardColor,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.White,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(
                        text = title,
                        color = Color.White) }
                )
            }
        }
// состояние которое в зависимости от индекса ( HOURS , DAYS ) вызывает либо функцию отображения по часам,  либо по дням
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            val list = when(index) {
                0 -> getWeatherByHours(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }
            MainList(list, currentDay )

        }
    }


}

// заполнение списк по часам
private fun getWeatherByHours(hours : String) : List<WeatherModel> {
    if(hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)

    val list = ArrayList<WeatherModel>()
    for(i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                "",
                item.getString("time"),
                item.getString("temp_c").toFloat().toInt().toString() + "°C",
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""
            )
        )
    }
    return list
}
