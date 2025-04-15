package com.alexcfa.yourweather

import android.app.Application
import androidx.room.Room
import com.alexcfa.yourweather.data.database.WeatherDatabase

class App : Application() {

    lateinit var database: WeatherDatabase
    private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

}