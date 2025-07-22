package com.alexcfa.yourweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHourlyForecasts(forecasts: List<DbHourlyForecast>)

    @Query("DELETE FROM hourly_forecast WHERE locationName = :location")
    suspend fun deleteOldForecasts(location: String)

    @Query("SELECT * FROM hourly_forecast WHERE locationName = :location ORDER BY time ASC")
    fun getHourlyForecastsByLocation(location: String): Flow<List<DbHourlyForecast>>

    @Query("SELECT * FROM hourly_forecast WHERE locationName = :location AND time LIKE :date || '%' ORDER BY time ASC")
    fun getHourlyForecastsForDay(location: String, date: String): Flow<List<DbHourlyForecast>>

    @Query("SELECT MAX(lastUpdated) FROM hourly_forecast WHERE locationName = :location")
    fun getLastUpdateTime(location: String): Flow<Long?>

    @Query("DELETE FROM hourly_forecast WHERE locationName = :location")
    suspend fun deleteHourlyForecastsByLocation(location: String)

}
