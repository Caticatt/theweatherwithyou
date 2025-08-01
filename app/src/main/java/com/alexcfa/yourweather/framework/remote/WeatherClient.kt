package com.alexcfa.yourweather.framework.remote

import com.alexcfa.yourweather.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
    chain
        .request()
        .newBuilder()
        .url(
            chain.request().url
                .newBuilder()
                .addQueryParameter("access_key", BuildConfig.WS_ACCESS_KEY)
                .build()
        )
        .build()

)

object WeatherClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { apiKeyAsQuery(it) }
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance = Retrofit.Builder()
        .baseUrl("https://api.weatherstack.com/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<WeatherService>()
}