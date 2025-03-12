package com.alexcfa.yourweather.data

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.alexcfa.yourweather.ui.common.getFromLocationCompat
import com.alexcfa.yourweather.ui.common.lastLocation

const val DEFAULT_REGION = "ES"

class RegionRepository(app: Application) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
    private val geocoder = Geocoder(app)

    suspend fun findLastRegion(): String = fusedLocationClient.lastLocation()?.toRegion() ?: DEFAULT_REGION

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses?.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }

}

