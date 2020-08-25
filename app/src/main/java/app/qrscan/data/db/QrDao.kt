package app.qrscan.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface QrDao {

    @Query("SELECT * FROM QrModel WHERE id LIKE :id LIMIT 1")
    suspend fun getQrModelWithId(id: Long): QrModel

    @Query("SELECT * FROM QrModel")
    suspend fun getAllQrModels(): List<QrModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: QrModel): Long

    @Update
    suspend fun update(model: QrModel)

    @Delete
    suspend fun delete(model: QrModel)
}
