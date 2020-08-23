package app.qrscan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QrModel::class], version = 1, exportSchema = false)
abstract class QrDatabase : RoomDatabase() {

    abstract fun qrDao(): QrDao
}
