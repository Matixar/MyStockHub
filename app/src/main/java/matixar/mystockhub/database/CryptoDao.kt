package matixar.mystockhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CryptoDao {
    @Insert
    fun insertAll(vararg crypto: Crypto)

    @Delete
    fun delete(crypto: Crypto)

    @Query("SELECT * FROM crypto")
    fun getAll(): List<Crypto>

    @Update
    fun updatecrypto(vararg crypto: Crypto)

    @Query("SELECT * FROM crypto WHERE search_string LIKE '%' || :string || '%'")
    fun getFromSearch(string: String): List<Crypto>

    @Query("SELECT * FROM crypto WHERE crypto_name LIKE :name")
    fun getFromName(name: String): Crypto
}