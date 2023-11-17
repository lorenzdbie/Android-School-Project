package com.example.androidschoolproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidschoolproject.network.CityCoordinates
import com.example.androidschoolproject.network.Pollution
import com.example.androidschoolproject.network.Weather
import com.example.androidschoolproject.network.WeatherCity

@Database(entities = [WeatherCity::class, CityCoordinates::class, Pollution::class, Weather::class ], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "weather-city_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
