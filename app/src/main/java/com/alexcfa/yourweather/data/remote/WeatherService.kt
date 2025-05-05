package com.alexcfa.yourweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current")
    suspend fun fetchCurrentLocationWeather(
        @Query("query") query: String,
        @Query("units") units: String,
    ): CurrentLocationResponse

    @GET("historical")
    suspend fun fetchHistoricalWeather(
        @Query("query") query: String,
        @Query("historical_date") historicalDate: String,
        @Query("hourly") hourly: Int,
        @Query("interval") interval: Int,
        @Query("units") units: String,
    ): HistoricalDataResponse

}