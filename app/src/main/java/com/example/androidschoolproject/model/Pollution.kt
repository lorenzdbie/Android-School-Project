package com.example.androidschoolproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * the main Data class for Pollution in the WeatherCity
 * @param id id of the pollution, auto generated
 * @param timeStamp time of the pollution data
 * @param aqiUsa air quality index of the pollution according to USA standards
 * @param mainUsa main pollutant of the pollution according to USA standards
 * @param aqiChina air quality index of the pollution according to China standards
 * @param mainChina main pollutant of the pollution according to China standards
 */
@Serializable
@Entity(tableName = TableNames.POLLUTION)
data class Pollution(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "pollutionID")
    val id: Int = 0,
    @SerialName("ts")
    @ColumnInfo(name = "pollutionTimeStamp")
    val timeStamp: String,
    @SerialName("aqius")
    val aqiUsa: Int,
    @SerialName("mainus")
    val mainUsa: String,
    @SerialName("aqicn")
    val aqiChina: Int,
    @SerialName("maincn")
    val mainChina: String,
)
