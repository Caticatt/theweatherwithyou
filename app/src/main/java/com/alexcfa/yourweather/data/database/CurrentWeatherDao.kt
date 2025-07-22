package com.alexcfa.yourweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentWeather(weather: DbCurrentWeather)

    @Query("SELECT * FROM current_weather ORDER BY lastUpdated DESC LIMIT 1")
    fun getLastCurrentWeather(): Flow<DbCurrentWeather?>

    @Query("SELECT * FROM current_weather WHERE locationName = :location")
    fun getCurrentWeatherByLocation(location: String): Flow<DbCurrentWeather?>

    @Query("DELETE FROM current_weather WHERE lastUpdated < :timestamp")
    suspend fun deleteOldRecords(timestamp: Long)

}
