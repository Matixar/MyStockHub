package matixar.mystockhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrencyDao {
    @Insert
    fun insertAll(vararg currency: Currency)

    @Delete
    fun delete(currency: Currency)

    @Query("SELECT * FROM currency")
    fun getAll(): List<Currency>

    @Update
    fun updateCurrency(vararg currency: Currency)

    @Query("SELECT * FROM currency WHERE search_string LIKE '%' || :string || '%'")
    fun getFromSearch(string: String): List<Currency>

    @Query("SELECT * FROM currency WHERE currency_name LIKE :name")
    fun getFromName(name: String): Currency
}