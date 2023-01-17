package matixar.mystockhub.ui.stock

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import matixar.mystockhub.database.Stock
import matixar.mystockhub.database.StockRepository

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    val allStocks: LiveData<List<Stock>> = repository.allStocks

    fun insert(stock: Stock) = viewModelScope.launch {
        repository.insert(stock)
    }

    fun searchStocks(name: String) = viewModelScope.launch {
        repository.getStocksFromSearch(name)
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