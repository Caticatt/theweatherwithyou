package com.alexcfa.yourweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexcfa.yourweather.data.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentWeather(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLastCurrentWeather(): CurrentWeatherEntity?

    @Query("SELECT * FROM current_weather WHERE locationName = :location")
    suspend fun getCurrentWeatherByLocation(location: String): CurrentWeatherEntity?

    @Query("DELETE FROM current_weather WHERE lastUpdated < :timestamp")
    suspend fun deleteOldRecords(timestamp: Long)

}
