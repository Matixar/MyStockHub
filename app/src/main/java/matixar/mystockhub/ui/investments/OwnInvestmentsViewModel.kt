package matixar.mystockhub.ui.investments

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.models.StockApiModel
import matixar.mystockhub.database.*

class OwnInvestmentsViewModel(private val repository: InvestmentsRepository,
                              private val stockRepository: StockRepository,
                              private val cryptoRepository: CryptoRepository,
                              private val goldRepository: GoldRepository) : ViewModel() {

    val allStock: LiveData<List<Stock>> = repository.allStocks.asLiveData()
    val allCrypto: LiveData<List<Crypto>> = repository.allCrypto.asLiveData()
    val allGold: LiveData<List<GoldEntity>> = repository.allGold.asLiveData()

    val gold: LiveData<List<Gold>> = goldRepository.goldList
    val coin: LiveData<Coin> = cryptoRepository.coin
    val stock: LiveData<StockApiModel> = stockRepository.stockData


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
        runBlocking {
            launch {
                stockRepository.getStockDataFromName(symbol)
            }
            launch {
                allStock.value?.map {
                    if(it.stockApiModel.symbol == symbol) it.currentPrice = stock.value?.price ?: it.stockApiModel.price
                }
            }
        }
    }

    fun getCoinData(symbol: String) = viewModelScope.launch {
        runBlocking {
            launch {
                cryptoRepository.getCoinInfo(symbol)
            }
            launch {
                allCrypto.value?.map {
                    if(it.coin.symbol == symbol) it.currentPrice = coin.value?.price?.toFloat() ?: it.coin.price.toFloat()
                }
            }
        }
    }

    fun getGoldData() = viewModelScope.launch {
        runBlocking {
            launch {
                goldRepository.getGoldPriceList()
            }
            launch {
                allGold.value?.map {
                    it.currentPrice = gold.value!![0].price
                }
            }
        }
    }

}

class OwnInvestmentsViewModelFactory(private val repository: InvestmentsRepository,
                                     private val stockRepository: StockRepository,
                                     private val cryptoRepository: CryptoRepository,
                                     private val goldRepository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OwnInvestmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OwnInvestmentsViewModel(repository, stockRepository, cryptoRepository, goldRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
