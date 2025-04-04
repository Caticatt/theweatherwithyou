package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.datasource.RegionDataSource

class RegionRepository(private val regionDataSource: RegionDataSource) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()

    suspend fun findLastRegionComplete(): String = regionDataSource.findLastRegionComplete()

}
