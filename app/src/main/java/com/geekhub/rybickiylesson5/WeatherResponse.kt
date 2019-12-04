package com.geekhub.rybickiylesson5


data class WeatherResponse(
    val cod: Int,
    val city: City,
    val list: List<ListWeather>

)