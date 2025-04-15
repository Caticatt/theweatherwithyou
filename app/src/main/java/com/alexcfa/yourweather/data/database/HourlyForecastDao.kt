package com.alexcfa.yourweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alexcfa.yourweather.data.HourlyForecastEntity

@Dao
interface HourlyForecastDao {

    @Insert
    suspend fun insertHourlyForecasts(forecasts: List<HourlyForecastEntity>)

    @Query("SELECT * FROM hourly_forecast WHERE locationName = :location ORDER BY time ASC")
    suspend fun getHourlyForecastsByLocation(location: String): List<HourlyForecastEntity>

    @Query("DELETE FROM hourly_forecast WHERE locationName = :location")
    suspend fun deleteHourlyForecastsByLocation(location: String)

    @Query("SELECT * FROM hourly_forecast WHERE locationName = :location AND time LIKE :date || '%' ORDER BY time ASC")
    suspend fun getHourlyForecastsForDay(location: String, date: String): List<HourlyForecastEntity>

}
