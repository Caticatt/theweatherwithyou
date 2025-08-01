package com.alexcfa.yourweather.data.datasource

import com.alexcfa.yourweather.domain.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}


