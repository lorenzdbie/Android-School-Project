package com.example.androidschoolproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import kotlinx.serialization.Serializable

/**
 * the main Data class for coordinates in the cityLocation
 * @param id id of the cityCoordinates, auto generated
 * @param longitude longitude of the city
 * @param latitude latitude of the city
 */
@Serializable
@Entity(tableName = TableNames.LOCATION)
data class CityCoordinates(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "locationID")
    val id: Int = 0,
    val longitude: Double,
    val latitude: Double,
)
