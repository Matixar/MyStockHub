package matixar.mystockhub.ui.stock

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.SearchResultModel
import matixar.mystockhub.API.models.StockApiModel
import matixar.mystockhub.database.entities.Stock
import matixar.mystockhub.database.repositories.StockRepository

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    val allStocks: LiveData<List<SearchResultModel>> = repository.allStocks
    val stockData: LiveData<StockApiModel> by lazy {
        repository.stockData
    }
    val stockDataLoaded: LiveData<Boolean> = repository.stockDataLoaded

    fun insert(stock: Stock) = viewModelScope.launch {
            repository.insert(stock)
    }

    fun searchStocks(name: String) = viewModelScope.launch {
            repository.getStocksFromSearch(name)
    }
    fun getStockData(name: String) = viewModelScope.launch {
            repository.getStockDataFromName(name)
    }
}

class StockViewModelFactory(private val repository: StockRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}