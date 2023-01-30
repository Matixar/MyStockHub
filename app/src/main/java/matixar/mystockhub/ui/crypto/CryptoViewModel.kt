package matixar.mystockhub.ui.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.database.entities.Crypto
import matixar.mystockhub.database.repositories.CryptoRepository

class CryptoViewModel(private val repository: CryptoRepository): ViewModel() {
    val allCoins: LiveData<List<Coin>> = repository.coinList
    val coin: LiveData<Coin> by lazy {
        repository.coin
    }
    val coinDataLoaded: LiveData<Boolean> = repository.coinDataLoaded

    fun getAllCoins() = viewModelScope.launch {
        repository.getCoinsList()
    }

    fun openCoinDetailsFragment(name: String) = viewModelScope.launch {
        repository.getCoinInfo(name)
    }

    fun insert(crypto: Crypto) = viewModelScope.launch {
        repository.insert(crypto)
    }
}

class CryptoViewModelFactory(private val repository: CryptoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CryptoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}