package app.qrscan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.qrscan.data.db.entity.Converters
import app.qrscan.data.db.entity.QrModel

@Database(entities = [QrModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QrDatabase : RoomDatabase() {
    abstract fun qrDao(): QrDao
}
