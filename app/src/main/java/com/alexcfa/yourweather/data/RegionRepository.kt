package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.datasource.GeocoderRegionDataSource

class RegionRepository(private val regionDataSource: GeocoderRegionDataSource) {

     suspend fun findLastRegionComplete(): String = regionDataSource.findLastRegionComplete()

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()

}
