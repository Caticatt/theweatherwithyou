package com.alexcfa.yourweather.data.datasource

import com.alexcfa.yourweather.domain.Location

interface RegionDataSource {
    suspend fun findLastRegion(): String
    suspend fun findLastRegionComplete(): String
    suspend fun Location.toRegion(): String
    suspend fun Location.toRegionComplete(): String
}


