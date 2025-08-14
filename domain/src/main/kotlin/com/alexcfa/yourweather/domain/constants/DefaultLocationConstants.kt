package com.alexcfa.yourweather.domain.constants

import com.alexcfa.yourweather.domain.Location

object DefaultLocationConstants {
    const val REGION = "ES"
    const val REGION_COMPLETE = "MADRID, ES"
    const val LATITUDE = "40.416775"
    const val LONGITUDE = "-3.703790"

    val DEFAULT_LOCATION = Location(LATITUDE.toDouble(), LONGITUDE.toDouble())

    const val UNITS = "m"
    const val INTERVAL = 1
    const val HOURLY = 1
}