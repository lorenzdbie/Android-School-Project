package com.example.androidschoolproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityLocation(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: DoubleArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityLocation

        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates.contentHashCode()
    }
}

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
