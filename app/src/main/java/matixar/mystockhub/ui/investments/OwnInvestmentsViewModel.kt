package matixar.mystockhub.ui.investments

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.models.StockApiModel
import matixar.mystockhub.database.*
import matixar.mystockhub.database.entities.Crypto
import matixar.mystockhub.database.entities.GoldEntity
import matixar.mystockhub.database.entities.Stock
import matixar.mystockhub.database.repositories.CryptoRepository
import matixar.mystockhub.database.repositories.GoldRepository
import matixar.mystockhub.database.repositories.InvestmentsRepository
import matixar.mystockhub.database.repositories.StockRepository

class OwnInvestmentsViewModel(private val repository: InvestmentsRepository,
                              private val stockRepository: StockRepository,
                              private val cryptoRepository: CryptoRepository,
                              private val goldRepository: GoldRepository
) : ViewModel() {

    val allStock: LiveData<List<Stock>> = repository.allStocks.asLiveData()
    val allCrypto: LiveData<List<Crypto>> = repository.allCrypto.asLiveData()
    val allGold: LiveData<List<GoldEntity>> = repository.allGold.asLiveData()

    val gold: LiveData<List<Gold>> = goldRepository.goldList
    val coin: LiveData<Coin> = cryptoRepository.coin
    val stock: LiveData<StockApiModel> = stockRepository.stockData

    val coinLoaded: LiveData<Boolean> = cryptoRepository.coinDataLoaded
    val stockLoaded: LiveData<Boolean> = stockRepository.stockDataLoaded

    fun delete(stock: Stock) = viewModelScope.launch {
        repository.delete(stock)
    }

    fun delete(crypto: Crypto) = viewModelScope.launch {
        repository.delete(crypto)
    }

    fun delete(goldEntity: GoldEntity) = viewModelScope.launch {
        repository.delete(goldEntity)
    }

    fun update(stock: Stock) = viewModelScope.launch {
        repository.update(stock)
    }

    fun update(crypto: Crypto) = viewModelScope.launch {
        repository.update(crypto)
    }

    fun update(goldEntity: GoldEntity) = viewModelScope.launch {
        repository.update(goldEntity)
    }

    fun getStockData(symbol: String) = viewModelScope.launch {
        stockRepository.getStockDataFromName(symbol)
    }

    fun getCoinData(symbol: String) = viewModelScope.launch {
        cryptoRepository.getCoinInfo(symbol)
    }

    fun getGoldData() = viewModelScope.launch {
        goldRepository.getGoldPriceList()
        allGold.value?.map {
            if(gold.value != null)
                it.currentPrice = gold.value!![0].price
            update(it)
        }
    }

}

class OwnInvestmentsViewModelFactory(private val repository: InvestmentsRepository,
                                     private val stockRepository: StockRepository,
                                     private val cryptoRepository: CryptoRepository,
                                     private val goldRepository: GoldRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OwnInvestmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OwnInvestmentsViewModel(repository, stockRepository, cryptoRepository, goldRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
