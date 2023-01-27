package matixar.mystockhub.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.CurrencyList
import matixar.mystockhub.database.CurrencyRepository

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    val currencyListToday: LiveData<CurrencyList> = repository.currencyList
    val currencyListYesterday: LiveData<CurrencyList> = repository.currencyListYesterday

    fun getCurrencyRates() = viewModelScope.launch {
        repository.getCurrencyListLast2Days()
    }

}

class CurrencyViewModelFactory(private val repository: CurrencyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}