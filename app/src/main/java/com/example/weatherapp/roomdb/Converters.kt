package com.example.weatherapp.roomdb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.weatherapp.models.Clouds
import com.example.weatherapp.models.Coord
import com.example.weatherapp.models.Main
import com.example.weatherapp.models.Sys
import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.Wind

class Converters {
    private val gson = Gson()

    // Type Converters for Clouds
    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        return gson.toJson(clouds)
    }

    @TypeConverter
    fun toClouds(cloudsString: String): Clouds {
        val type = object : TypeToken<Clouds>() {}.type
        return gson.fromJson(cloudsString, type)
    }

    // Type Converters for Coord
    @TypeConverter
    fun fromCoord(coord: Coord): String {
        return gson.toJson(coord)
    }

    @TypeConverter
    fun toCoord(coordString: String): Coord {
        val type = object : TypeToken<Coord>() {}.type
        return gson.fromJson(coordString, type)
    }

    // Type Converters for Main
    @TypeConverter
    fun fromMain(main: Main): String {
        return gson.toJson(main)
    }

    @TypeConverter
    fun toMain(mainString: String): Main {
        val type = object : TypeToken<Main>() {}.type
        return gson.fromJson(mainString, type)
    }

    // Type Converters for Sys
    @TypeConverter
    fun fromSys(sys: Sys): String {
        return gson.toJson(sys)
    }

    @TypeConverter
    fun toSys(sysString: String): Sys {
        val type = object : TypeToken<Sys>() {}.type
        return gson.fromJson(sysString, type)
    }

    // Type Converters for Weather
    @TypeConverter
    fun fromWeather(weather: List<Weather>): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun toWeather(weatherString: String): List<Weather> {
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(weatherString, type)
    }

    // Type Converters for Wind
    @TypeConverter
    fun fromWind(wind: Wind): String {
        return gson.toJson(wind)
    }

    @TypeConverter
    fun toWind(windString: String): Wind {
        val type = object : TypeToken<Wind>() {}.type
        return gson.fromJson(windString, type)
    }
}
