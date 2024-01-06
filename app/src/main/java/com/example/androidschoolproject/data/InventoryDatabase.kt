package com.example.androidschoolproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidschoolproject.model.CityCoordinates
import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import com.example.androidschoolproject.model.WeatherCity

/**
 * the main Database class which uses Room and holld all the tables
 */
@Database(
    entities = [WeatherCity::class, CityCoordinates::class, Pollution::class, Weather::class],
    version = 1,
    exportSchema = false,
)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    "weather-city_database",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
