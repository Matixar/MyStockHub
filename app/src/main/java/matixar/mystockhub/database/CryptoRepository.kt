package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.API.CoinLibApiInterface
import matixar.mystockhub.API.models.CoinList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoRepository {
    val coinList = MutableLiveData<List<Coin>>()
    val coin = MutableLiveData<Coin>()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCoinsList() {
        val api = CoinLibApiInterface.create().getCoinList(CoinLibApiInterface.API_KEY)
        api.enqueue(object : Callback<CoinList> {
            override fun onResponse(call: Call<CoinList>, response: Response<CoinList>) {
                coinList.value = response.body()?.coins
            }

            override fun onFailure(call: Call<CoinList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCoinInfo(symbol: String) {
        val api = CoinLibApiInterface.create().getCoin(CoinLibApiInterface.API_KEY, symbol)
        api.enqueue(object : Callback<Coin> {
            override fun onResponse(call: Call<Coin>, response: Response<Coin>) {
                coin.value = response.body()
            }

            override fun onFailure(call: Call<Coin>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}