package matixar.mystockhub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import matixar.mystockhub.database.dao.CryptoDao
import matixar.mystockhub.database.dao.GoldDao
import matixar.mystockhub.database.dao.StockDao
import matixar.mystockhub.database.entities.Crypto
import matixar.mystockhub.database.entities.GoldEntity
import matixar.mystockhub.database.entities.Stock

@Database(entities = [GoldEntity::class, Crypto::class, Stock::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
    abstract fun stockDao(): StockDao
    abstract fun goldDao(): GoldDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}