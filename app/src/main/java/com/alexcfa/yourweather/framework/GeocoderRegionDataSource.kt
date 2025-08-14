package com.alexcfa.yourweather.framework

import android.location.Geocoder

import com.alexcfa.yourweather.data.datasource.RegionDataSource
import com.alexcfa.yourweather.domain.Location
import com.alexcfa.yourweather.domain.constants.DefaultLocationConstants.REGION
import com.alexcfa.yourweather.domain.constants.DefaultLocationConstants.REGION_COMPLETE
import com.alexcfa.yourweather.ui.common.getFromLocationCompat

class GeocoderRegionDataSource(
    private val geocoder: Geocoder,
    private val locationDatasource: PlayServicesLocationDataSource
) : RegionDataSource {

    override suspend fun findLastRegion(): String =
        locationDatasource.findLastLocation()?.toRegion() ?: REGION

    override suspend fun findLastRegionComplete(): String =
        locationDatasource.findLastLocation()?.toRegionComplete() ?: REGION_COMPLETE

    override suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses?.firstOrNull()?.countryCode
        return region ?: REGION
    }

    override suspend fun Location.toRegionComplete(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)

        return addresses?.firstOrNull()?.let { address ->
            buildString {
                address.locality?.let { locality ->
                    append(locality)
                    address.countryCode?.let { append(", $it") }
                } ?: address.countryCode?.let { append(it) }
            }.takeIf { it.isNotEmpty() }
        } ?: REGION_COMPLETE
    }
}

