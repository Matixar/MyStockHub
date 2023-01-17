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
    fun delete(stock: Stock)

    @Query("SELECT * FROM Stock")
    fun getAll(): Flow<List<Stock>>

    @Update
    fun updatestock(vararg stock: Stock)

    @Query("SELECT * FROM stock WHERE search_string LIKE '%' || :string || '%'")
    fun getFromSearch(string: String): List<Stock>

    @Query("SELECT * FROM stock WHERE stock_name LIKE :name")
    fun getFromName(name: String): Stock

    @Query("SELECT * FROM stock WHERE search_string LIKE :name")
    fun getStock(name: String): Stock?
}