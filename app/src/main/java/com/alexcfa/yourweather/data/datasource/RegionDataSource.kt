package com.alexcfa.yourweather.data.datasource

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.alexcfa.yourweather.ui.common.getFromLocationCompat

const val DEFAULT_REGION = "ES"
const val DEFAULT_REGION_COMPLETE = "MADRID, ES"

class RegionDataSource (app: Application , private val locationDatasource: LocationDatasource){

    private val geocoder = Geocoder(app)

     suspend fun findLastRegion() : String = locationDatasource.findLastLocation()?.toRegion() ?: DEFAULT_REGION

      suspend fun findLastRegionComplete() : String = locationDatasource.findLastLocation()?.toRegionComplete() ?: DEFAULT_REGION_COMPLETE

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses?.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }

    private suspend fun Location.toRegionComplete(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val regionComplete = addresses?.firstOrNull()?.locality + addresses?.firstOrNull()?.countryCode
        return regionComplete ?: DEFAULT_REGION_COMPLETE
    }

}
