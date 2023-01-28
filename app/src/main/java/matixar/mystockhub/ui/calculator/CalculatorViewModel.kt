package matixar.mystockhub.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.CurrencyList
import matixar.mystockhub.database.CurrencyRepository
import matixar.mystockhub.ui.currency.CurrencyViewModel

class CalculatorViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {
    val currencyList: LiveData<CurrencyList> = currencyRepository.currencyList

    fun getCurrencyList() = viewModelScope.launch {
        currencyRepository.getCurrencyListLast2Days()
    }

}

class CalculatorViewModelFactory(private val repository: CurrencyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}