package app.qrscan.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.qrscan.util.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class QrModel (
    val time: Long,
    var title: String,
    val text: String,
    val type: String,
    val format: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var favorite: Boolean = false
    var latLon = LatLon(0.0, 0.0)
}
