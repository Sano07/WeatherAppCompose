package com.example.weatherappcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.data.FavCityModel
import com.example.weatherappcompose.ui.theme.CardColor

@Composable
fun SavedLocation(favList: MutableState<List<FavCityModel>>, onClickSearchFav: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        itemsIndexed(favList.value) { _, item ->
            UIfavCity(item, onClickSearchFav)
        }
    }
}

@Composable
fun UIfavCity(item: FavCityModel, onClickSearchFav: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                onClickSearchFav.invoke(item.city)
            },
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.city, color = Color.White, style = TextStyle(fontSize = 20.sp))
            Text(text = item.currentTemp,
            color = Color.White,
            style = TextStyle(fontSize = 30.sp)
            )
            AsyncImage(
                model = "https:${item.conditionIcon}",
                contentDescription = "im_weather3",
                modifier = Modifier
                    .size(40.dp),
            )
        }
    }
}