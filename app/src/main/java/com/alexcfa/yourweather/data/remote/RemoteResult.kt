package com.alexcfa.yourweather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentLocationResponse(
    @SerialName("request")
    val request: RemoteRequest? = null,
    @SerialName("location")
    val location: RemoteLocation? = null,
    @SerialName("current")
    val current: RemoteCurrent? = null
)

@Serializable
data class HistoricalDataResponse(
    @SerialName("request")
    val request: RemoteRequest? = null,
    @SerialName("location")
    val location: RemoteLocation? = null,
    @SerialName("current")
    val current: RemoteCurrent? = null,
    @SerialName("historical")
    val historical: Map<String, RemoteHistorical>? = null
)

@Serializable
data class RemoteHistorical(
    @SerialName("date")
    val date: String? = null,
    @SerialName("date_epoch")
    val dateEpoch: Int? = null,
    @SerialName("astro")
    val astro: RemoteAstro? = null,
    @SerialName("mintemp")
    val mintemp: Int? = null,
    @SerialName("maxtemp")
    val maxtemp: Int? = null,
    @SerialName("avgtemp")
    val avgtemp: Int? = null,
    @SerialName("totalsnow")
    val totalsnow: Int? = null,
    @SerialName("sunhour")
    val sunhour: Int? = null,
    @SerialName("uv_index")
    val uvIndex: Int? = null,
    @SerialName("hourly")
    val hourlyList: List<RemoteHourly>? = null
)


@Serializable
data class RemoteRequest(
    @SerialName("type")
    val type: String? = null,
    @SerialName("query")
    val query: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("unit")
    val unit: String? = null
)

@Serializable
data class RemoteLocation(
    @SerialName("name")
    val name: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("region")
    val region: String? = null,
    @SerialName("lat")
    val lat: String? = null,
    @SerialName("lon")
    val lon: String? = null,
    @SerialName("timezone_id")
    val timezoneId: String? = null,
    @SerialName("localtime")
    val localtime: String? = null,
    @SerialName("localtime_epoch")
    val localtimeEpoch: Int? = null,
    @SerialName("utc_offset")
    val utcOffset: String? = null
)

@Serializable
data class RemoteCurrent(
    @SerialName("observation_time")
    val observationTime: String? = null,
    @SerialName("temperature")
    val temperature: Int? = null,
    @SerialName("weather_code")
    val weatherCode: Int? = null,
    @SerialName("weather_icons")
    val weatherIcons: List<String>? = null,
    @SerialName("weather_descriptions")
    val weatherDescriptions: List<String>? = null,
    @SerialName("wind_speed")
    val windSpeed: Int? = null,
    @SerialName("wind_degree")
    val windDegree: Int? = null,
    @SerialName("wind_dir")
    val windDir: String? = null,
    @SerialName("pressure")
    val pressure: Int? = null,
    @SerialName("precip")
    val precip: Double? = null,
    @SerialName("humidity")
    val humidity: Int? = null,
    @SerialName("cloudcover")
    val cloudcover: Int? = null,
    @SerialName("feelslike")
    val feelslike: Int? = null,
    @SerialName("uv_index")
    val uvIndex: Int? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("is_day")
    val isDay: String? = null
)

@Serializable
data class RemoteAstro(
    @SerialName("sunrise")
    val sunrise: String? = null,
    @SerialName("sunset")
    val sunset: String? = null,
    @SerialName("moonrise")
    val moonrise: String? = null,
    @SerialName("moonset")
    val moonset: String? = null,
    @SerialName("moon_phase")
    val moonPhase: String? = null,
    @SerialName("moon_illumination")
    val moonIllumination: Int? = null
)

@Serializable
data class RemoteHourly(
    @SerialName("time")
    val time: String? = null,
    @SerialName("temperature")
    val temperature: Int? = null,
    @SerialName("wind_speed")
    val windSpeed: Int? = null,
    @SerialName("weather_icons")
    val weatherIcons: List<String>? = null,
    @SerialName("weather_descriptions")
    val weatherDescriptions: List<String>? = null,
    @SerialName("precip")
    val precip: Double? = null,
    @SerialName("pressure")
    val pressure: Int? = null
)

