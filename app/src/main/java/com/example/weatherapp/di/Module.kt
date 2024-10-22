package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.network.ApiInterface
import com.example.weatherapp.roomdb.MyDao
import com.example.weatherapp.roomdb.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) // Singleton component for app-wide dependencies
@Module
object Module {

    // Provides Retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/") // Base URL for weather API
            .addConverterFactory(GsonConverterFactory.create()) // JSON converter for API response
            .build()
    }

    // Provides ApiInterface instance for API calls
    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    // Provides Room Database instance
    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "weather_db" // Name of the database
        )
            .fallbackToDestructiveMigration() // Handles migrations without providing custom migration logic
            .build()
    }

    // Provides DAO for Room Database operations
    @Singleton
    @Provides
    fun provideMyDao(database: MyDatabase): MyDao {
        return database.getDao()
    }
}
