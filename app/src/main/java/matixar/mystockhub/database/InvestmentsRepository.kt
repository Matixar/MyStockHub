package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class InvestmentsRepository(private val stockDao: StockDao,
                            private val goldDao: GoldDao,
                            private val cryptoDao: CryptoDao) {

    val allStocks: Flow<List<Stock>> = stockDao.getAll()
    val allCrypto: Flow<List<Crypto>> = cryptoDao.getAll()
    val allGold: Flow<List<GoldEntity>> = goldDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(stock: Stock) {
        stockDao.delete(stock)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(crypto: Crypto) {
        cryptoDao.delete(crypto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(gold: GoldEntity) {
        goldDao.delete(gold)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(stock: Stock) {
        stockDao.updatestock(stock)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(crypto: Crypto) {
        cryptoDao.updateCrypto(crypto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(gold: GoldEntity) {
        goldDao.updateGold(gold)
    }
}