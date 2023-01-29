package matixar.mystockhub.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert
    fun insertAll(vararg stock: Stock)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stock: Stock)

    @Delete
    suspend fun delete(stock: Stock)

    @Query("SELECT * FROM Stock")
    fun getAll(): Flow<List<Stock>>

    @Update
    suspend fun updatestock(vararg stock: Stock)
}