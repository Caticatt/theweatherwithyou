package com.alexcfa.yourweather.framework

import android.location.Geocoder

import com.alexcfa.yourweather.data.datasource.DEFAULT_REGION
import com.alexcfa.yourweather.data.datasource.DEFAULT_REGION_COMPLETE
import com.alexcfa.yourweather.data.datasource.RegionDataSource
import com.alexcfa.yourweather.domain.Location
import com.alexcfa.yourweather.ui.common.getFromLocationCompat

class GeocoderRegionDataSource(
    private val geocoder: Geocoder,
    private val locationDatasource: PlayServicesLocationDataSource
) : RegionDataSource {

    override suspend fun findLastRegion(): String =
        locationDatasource.findLastLocation()?.toRegion() ?: DEFAULT_REGION

    override suspend fun findLastRegionComplete(): String =
        locationDatasource.findLastLocation()?.toRegionComplete() ?: DEFAULT_REGION_COMPLETE

    override suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses?.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }

    override suspend fun Location.toRegionComplete(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val regionComplete =
            addresses?.firstOrNull()?.locality + addresses?.firstOrNull()?.countryCode
        return regionComplete ?: DEFAULT_REGION_COMPLETE
    }

}

