package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.datasource.RegionDataSource


const val DEFAULT_LATITUDE = "40.416775" // Default latitude string
const val DEFAULT_LONGITUDE = "-3.703790" // Default longitude string

class RegionRepository(private val regionDataSource: RegionDataSource) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()

    suspend fun findLastRegionComplete(): String = regionDataSource.findLastRegionComplete()

}
