package matixar.mystockhub

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import matixar.mystockhub.database.*

class MyStockHubApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LocalDatabase.getDatabase(this) }
    val stockRepository by lazy { StockRepository(database.stockDao()) }
    val cryptoRepository by lazy {CryptoRepository(database.cryptoDao())}
    val goldRepository by lazy {GoldRepository(database.goldDao())}
    val currencyRepository by lazy {CurrencyRepository()}
    val investmentsRepository by lazy {InvestmentsRepository(database.stockDao(), database.goldDao(), database.cryptoDao())}
}