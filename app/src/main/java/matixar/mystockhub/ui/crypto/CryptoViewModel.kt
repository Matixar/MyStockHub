package matixar.mystockhub.ui.crypto

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.database.Crypto
import matixar.mystockhub.database.CryptoRepository

class CryptoViewModel(private val repository: CryptoRepository): ViewModel() {
    val allCoins: LiveData<List<Coin>> = repository.coinList
    val coin: LiveData<Coin> by lazy {
        repository.coin
    }

    fun getAllCoins() = viewModelScope.launch {
        repository.getCoinsList()
    }

    fun getCoinData(symbol: String) = viewModelScope.launch {
        repository.getCoinInfo(symbol)
    }

    fun openCoinDetailsFragment(name: String, view: View) = viewModelScope.launch {
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