package com.geekhub.rybickiylesson5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast?")
    fun getCurrentWeather(@Query("q") location: String,@Query("APPID") key: String): Call<WeatherResponse>
}