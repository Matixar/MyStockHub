package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.NBPApiInterface
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
            }

            override fun onFailure(call: Call<Gold>, t: Throwable) {
                TODO("Not yet implemented")
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
            }

            override fun onFailure(call: Call<List<Gold>>, t: Throwable) {
                println(t.message)
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(goldEntity: GoldEntity) {
        goldDao.insert(goldEntity)
    }
}