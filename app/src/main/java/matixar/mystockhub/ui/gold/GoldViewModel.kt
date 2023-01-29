package matixar.mystockhub.ui.gold

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.database.GoldEntity
import matixar.mystockhub.database.GoldRepository

class GoldViewModel(private val repository: GoldRepository) : ViewModel() {
    val gold: LiveData<Gold> = repository.gold
    val goldList: LiveData<List<Gold>> = repository.goldList

    fun getGoldPrice() = viewModelScope.launch {
        repository.getGoldPrice()
    }

    fun getGoldListPrice() = viewModelScope.launch {
        repository.getGoldPriceList()
    }

    fun insert(goldEntity: GoldEntity) = viewModelScope.launch {
        repository.insert(goldEntity)
    }

}

class GoldViewModelFactory(private val repository: GoldRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoldViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoldViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}