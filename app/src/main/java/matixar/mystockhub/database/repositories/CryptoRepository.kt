package matixar.mystockhub.database.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.CoinLibApiInterface
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.API.models.CoinList
import matixar.mystockhub.database.entities.Crypto
import matixar.mystockhub.database.dao.CryptoDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoRepository(private val cryptoDao: CryptoDao) {
    val coinList = MutableLiveData<List<Coin>>()
    val coin = MutableLiveData<Coin>()
    val coinDataLoaded = MutableLiveData<Boolean>()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCoinsList() {
        val api = CoinLibApiInterface.create().getCoinList(CoinLibApiInterface.API_KEY)
        api.enqueue(object : Callback<CoinList> {
            override fun onResponse(call: Call<CoinList>, response: Response<CoinList>) {
                coinList.value = response.body()?.coins
                Log.d("Retrofit", "onResponse() called with: call = $call, response = ${response.body()}")
            }

            override fun onFailure(call: Call<CoinList>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getCoinsList()", t)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCoinInfo(symbol: String) {
        coinDataLoaded.value = false
        val api = CoinLibApiInterface.create().getCoin(CoinLibApiInterface.API_KEY, symbol)
        api.enqueue(object : Callback<Coin> {
            override fun onResponse(call: Call<Coin>, response: Response<Coin>) {
                coin.value = response.body()
                coinDataLoaded.value = true
                Log.d("Retrofit", "onResponse() called with: call = $call, response = ${response.body()}")
            }

            override fun onFailure(call: Call<Coin>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getCoinInfo(symbol = $symbol)", t)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(crypto: Crypto) {
        cryptoDao.insert(crypto)
    }

}