package matixar.mystockhub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import matixar.mystockhub.util.StockOpenTime

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to home fragment!\n" + if(StockOpenTime.isStockOpen()) "Stock market is open right now" else "Stock market is closed right now"
    }
    val text: LiveData<String> = _text
}