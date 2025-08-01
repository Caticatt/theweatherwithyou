package com.alexcfa.yourweather.framework

import android.annotation.SuppressLint
import com.alexcfa.yourweather.data.datasource.LocationDataSource
import com.alexcfa.yourweather.domain.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import android.location.Location as AndroidLocation

class PlayServicesLocationDataSource(private val fusedLocationClient: FusedLocationProviderClient) :
    LocationDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun findLastLocation(): Location? = fusedLocationClient.lastLocation()
}

/*@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        val task = lastLocation
        task.addOnSuccessListener { location ->
            continuation.resume(location?.toDomainLocation())
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}*/

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location?.toDomainLocation())
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

 private fun AndroidLocation.toDomainLocation(): Location = Location(latitude, longitude)

