package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.NBPApiInterface
import matixar.mystockhub.API.models.CurrencyList
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
                println("test1")
                println(response.body())
                currencyList.value = response.body()?.get(1)
                currencyListYesterday.value = response.body()?.get(0)
            }

            override fun onFailure(call: Call<List<CurrencyList>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}