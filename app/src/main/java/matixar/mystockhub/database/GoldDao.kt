package matixar.mystockhub.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GoldDao {
    @Insert
    fun insertAll(vararg gold: GoldEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gold: GoldEntity)

    @Delete
    suspend fun delete(goldEntity: GoldEntity)

    @Query("SELECT * FROM goldentity")
    fun getAll(): Flow<List<GoldEntity>>

    @Update
    suspend fun updateGold(vararg goldEntity: GoldEntity)
}