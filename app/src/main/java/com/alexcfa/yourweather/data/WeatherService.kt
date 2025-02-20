package com.alexcfa.yourweather.data

import CurrentLocationResponse
import HistoricalDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current")
    suspend fun fetchCurrentLocationWeather(
        @Query("query") query: String,
        @Query("units") units: String,//m
    ): CurrentLocationResponse

    @GET("historical")
    suspend fun fetchHistoricalWeather(
        @Query("query") query: String,
        @Query("historical_date") historicalDate: String,
        @Query("hourly") hourly: Int,//1 on 0 off weather data split hourly
        @Query("interval") interval: Int, //if hourly enable, this define de interval =>1
        @Query("units") units: String, //m
    ): HistoricalDataResponse

}