package com.alexcfa.yourweather.data.datasource

import com.alexcfa.yourweather.domain.Location


const val DEFAULT_REGION = "ES"
const val DEFAULT_REGION_COMPLETE = "MADRID, ES"

interface RegionDataSource {
    suspend fun findLastRegion(): String
    suspend fun findLastRegionComplete(): String
    suspend fun Location.toRegion(): String
    suspend fun Location.toRegionComplete(): String
}


