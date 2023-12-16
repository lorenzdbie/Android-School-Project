package com.example.androidschoolproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableNames.WEATHER)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "weatherID")
    val id: Int = 0,
    @SerialName("ts")
    @ColumnInfo(name = "weatherTimeStamp")
    val timeStamp: String,
    @SerialName("tp")
    val temperature: Double,
    @SerialName("pr")
    val atmosphericPressure: Int,
    @SerialName("hu")
    val humidity: Int,
    @SerialName("ws")
    val windSpeed: Float,
    @SerialName("wd")
    val windDirection: Int,
    @SerialName("ic")
    val weatherIcon: String,
)
