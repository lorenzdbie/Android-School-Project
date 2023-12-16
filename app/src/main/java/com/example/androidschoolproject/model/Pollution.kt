package com.example.androidschoolproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
