package com.alexcfa.yourweather.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentLocationResponse(
    @SerialName("request")
    val request: Request?,
    @SerialName("location")
    val location: Location?,
    @SerialName("current")
    val current: Current?
)

@Serializable
data class HistoricalDataResponse(
    @SerialName("request")
    val request: Request?,
    @SerialName("location")
    val location: Location?,
    @SerialName("current")
    val current: Current?,
    @SerialName("historical")
    val historical: Map<String, Historical>?
)

@Serializable
data class Historical(
    @SerialName("date")
    val date: String?,
    @SerialName("date_epoch")
    val dateEpoch: Int?,
    @SerialName("astro")
    val astro: Astro?,
    @SerialName("mintemp")
    val mintemp: Int?,
    @SerialName("maxtemp")
    val maxtemp: Int?,
    @SerialName("avgtemp")
    val avgtemp: Int?,
    @SerialName("totalsnow")
    val totalsnow: Int?,
    @SerialName("sunhour")
    val sunhour: Int?,
    @SerialName("uv_index")
    val uvIndex: Int?,
    @SerialName("hourly")
    val hourlyList: List<Hourly>?
)


@Serializable
data class Request(
    @SerialName("type")
    val type: String?,
    @SerialName("query")
    val query: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("unit")
    val unit: String?
)

@Serializable
data class Location(
    @SerialName("name")
    val name: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("region")
    val region: String?,
    @SerialName("lat")
    val lat: String?,
    @SerialName("lon")
    val lon: String?,
    @SerialName("timezone_id")
    val timezoneId: String?,
    @SerialName("localtime")
    val localtime: String?,
    @SerialName("localtime_epoch")
    val localtimeEpoch: Int?,
    @SerialName("utc_offset")
    val utcOffset: String?
)

@Serializable
data class Current(
    @SerialName("observation_time")
    val observationTime: String?,
    @SerialName("temperature")
    val temperature: Int?,
    @SerialName("weather_code")
    val weatherCode: Int?,
    @SerialName("weather_icons")
    val weatherIcons: List<String>?,
    @SerialName("weather_descriptions")
    val weatherDescriptions: List<String>?,
    @SerialName("wind_speed")
    val windSpeed: Int?,
    @SerialName("wind_degree")
    val windDegree: Int?,
    @SerialName("wind_dir")
    val windDir: String?,
    @SerialName("pressure")
    val pressure: Int?,
    @SerialName("precip")
    val precip: Double?,
    @SerialName("humidity")
    val humidity: Int?,
    @SerialName("cloudcover")
    val cloudcover: Int?,
    @SerialName("feelslike")
    val feelslike: Int?,
    @SerialName("uv_index")
    val uvIndex: Int?,
    @SerialName("visibility")
    val visibility: Int?,
    @SerialName("is_day")
    val isDay: String?
)

@Serializable
data class Astro(
    @SerialName("sunrise")
    val sunrise: String?,
    @SerialName("sunset")
    val sunset: String?,
    @SerialName("moonrise")
    val moonrise: String?,
    @SerialName("moonset")
    val moonset: String?,
    @SerialName("moon_phase")
    val moonPhase: String?,
    @SerialName("moon_illumination")
    val moonIllumination: Int?
)

@Serializable
data class Hourly(
    @SerialName("time")
    val time: String?,
    @SerialName("temperature")
    val temperature: Int?,
    @SerialName("wind_speed")
    val windSpeed: Int?,
    @SerialName("weather_icons")
    val weatherIcons: List<String>,
    @SerialName("weather_descriptions")
    val weatherDescriptions: List<String>?,
    @SerialName("precip")
    val precip: Double? ,
    @SerialName("pressure")
    val pressure: Int?
)

