package app.qrscan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QrModel (
        val time: Long,
        val title: String,
        val text: String,
        val type: String,
        val format: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var favorite: Boolean = false
}
