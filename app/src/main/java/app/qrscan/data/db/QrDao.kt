package app.qrscan.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
abstract class QrDao {

    @Query("SELECT * FROM QrModel WHERE id LIKE :id LIMIT 1")
    abstract suspend fun getQrModelWithId(id: Long): QrModel

    @Query("SELECT * FROM QrModel")
    abstract suspend fun getAllQrModels(): List<QrModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(model: QrModel): Long

    @Transaction
    open suspend fun updateAndGetFavorite(id: Long): Boolean {
        val model = getQrModelWithId(id).also { it.favorite = it.favorite.not() }
        update(model)
        return model.favorite
    }

    @Update
    abstract suspend fun update(model: QrModel)

    @Delete
    abstract suspend fun delete(model: QrModel)

}
