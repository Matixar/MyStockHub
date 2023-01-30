package matixar.mystockhub.database.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.NBPApiInterface
import matixar.mystockhub.database.dao.GoldDao
import matixar.mystockhub.database.entities.GoldEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoldRepository(private val goldDao: GoldDao) {
    val gold = MutableLiveData<Gold>()
    val goldList = MutableLiveData<List<Gold>>()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGoldPrice() {
        val api = NBPApiInterface.create().getGoldPriceToday()
        api.enqueue(object : Callback<Gold> {
            override fun onResponse(call: Call<Gold>, response: Response<Gold>) {
                gold.value = response.body()
                Log.d("Retrofit", "onResponse() called with: call = $call, response = ${response.body()}")
            }

            override fun onFailure(call: Call<Gold>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getGoldPrice()", t)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGoldPriceList() {
        val api = NBPApiInterface.create().getGoldPrice2LastDays()
        api.enqueue(object : Callback<List<Gold>> {
            override fun onResponse(call: Call<List<Gold>>, response: Response<List<Gold>>) {
                goldList.value = response.body()
                Log.d("Retrofit", "onResponse() called with: call = $call, response = ${response.body()}")
            }

            override fun onFailure(call: Call<List<Gold>>, t: Throwable) {
                Log.e("Retrofit", "onFailure: getGoldPriceList()", t)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(goldEntity: GoldEntity) {
        goldDao.insert(goldEntity)
    }
}