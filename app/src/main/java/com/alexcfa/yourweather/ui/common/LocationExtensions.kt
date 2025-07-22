package com.alexcfa.yourweather.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

data class LocationDataString(val latitude: String, val longitude: String)

const val DEFAULT_LATITUDE = "40.416775" // Default latitude string
const val DEFAULT_LONGITUDE = "-3.703790" // Default longitude string

suspend fun Context.getLocationDataString(): LocationDataString {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    val location = fusedLocationClient.lastLocation()
    val latitude = location?.latitude?.toString() ?: DEFAULT_LATITUDE
    val longitude = location?.longitude?.toString() ?: DEFAULT_LONGITUDE
    return LocationDataString(latitude, longitude)
}

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

@Suppress("DEPRECATION")
suspend fun Geocoder.getFromLocationCompat(
    @FloatRange(from = -90.0, to = 90.0) latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) longitude: Double,
    @IntRange maxResults: Int
): List<Address>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    suspendCancellableCoroutine { continuation ->
        getFromLocation(latitude, longitude, maxResults) {
            continuation.resume(it)
        }
    }
} else {
    withContext(Dispatchers.IO) {
        getFromLocation(latitude, longitude, maxResults) ?: emptyList()
    }
}