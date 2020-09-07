package app.qrscan.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromLatLonToJson(latLon: LatLon): String {
        return "{\"lat\":${latLon.lat},\"lon\":${latLon.lon}}"
    }

    @TypeConverter
    fun fromJsonToLatLon(json: String): LatLon {
        return Gson().fromJson(json, LatLon::class.java)
    }
}
