package matixar.mystockhub.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import matixar.mystockhub.database.entities.Crypto

@Dao
interface CryptoDao {
    @Insert
    fun insertAll(vararg crypto: Crypto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crypto: Crypto)

    @Delete
    suspend fun delete(crypto: Crypto)

    @Query("SELECT * FROM crypto")
    fun getAll(): Flow<List<Crypto>>

    @Update
    suspend fun updateCrypto(vararg crypto: Crypto)

}