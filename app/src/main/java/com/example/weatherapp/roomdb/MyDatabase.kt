package com.example.weatherapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.models.Clouds
import com.example.weatherapp.models.Coord
import com.example.weatherapp.models.Main
import com.example.weatherapp.models.Sys

import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.Wind
import com.example.weatherapp.models.weatherApp


@Database(entities = [weatherApp::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase()  {
    abstract fun getDao(): MyDao


    // before using dagger hilt with roomdb
//    companion object {
//        lateinit var myDatabase: MyDatabase
//        fun init(context: Context){
//            myDatabase =Room.databaseBuilder(context, MyDatabase::class.java
//                ,"myDatabase")
////                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//        }}

    }


