package matixar.mystockhub

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import matixar.mystockhub.database.*

class MyStockHubApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LocalDatabase.getDatabase(this, applicationScope) }
    val stockRepository by lazy { StockRepository(database.stockDao()) }
    val cryptoRepository by lazy {CryptoRepository()}
    val goldRepository by lazy {GoldRepository()}
    val currencyRepository by lazy {CurrencyRepository()}
}