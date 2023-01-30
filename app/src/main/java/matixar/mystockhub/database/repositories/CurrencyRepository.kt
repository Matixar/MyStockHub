package matixar.mystockhub.database.repositories

import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.NBPApiInterface
import matixar.mystockhub.API.models.CurrencyList
import matixar.mystockhub.API.models.SingleCurrencyList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepository {
    val currencyList = MutableLiveData<CurrencyList>()
    val currencyListYesterday = MutableLiveData<CurrencyList>()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCurrencyListLast2Days() {
        val api = NBPApiInterface.create().getCurrenciesExchangeRatesLast2Days()
        api.enqueue(object : Callback<List<CurrencyList>> {
            override fun onResponse(
                call: Call<List<CurrencyList>>,
                response: Response<List<CurrencyList>>
            ) {
                currencyList.value = response.body()?.get(1)
                currencyListYesterday.value = response.body()?.get(0)
            }

            override fun onFailure(call: Call<List<CurrencyList>>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getCurrencyListLast2Days()", t)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCurrencyExchangeRate(symbol: String, preferences: SharedPreferences) {
        val api = NBPApiInterface.create().getCurrencyRate(symbol)
        api.enqueue(object : Callback<SingleCurrencyList> {
            override fun onResponse(
                call: Call<SingleCurrencyList>,
                response: Response<SingleCurrencyList>
            ) {
                preferences.edit().putFloat("currency_$symbol", response.body()?.rates?.first()!!.mid).commit()
            }

            override fun onFailure(call: Call<SingleCurrencyList>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getCurrencyExchangeRate(symbol = $symbol, preferences = $preferences)", t)
            }

        })
    }
}