package com.example.weatherapp.network

import com.example.weatherapp.models.weatherApp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Response<weatherApp>

    // Add other API endpoints as needed
    // For example:
    // @GET("another_endpoint")
    // suspend fun getAnotherData(): Response<AnotherModel>
    @GET("weather/{city}")
    suspend fun getWeatherByCity(@Query("city") city: String): Response<weatherApp>


}
