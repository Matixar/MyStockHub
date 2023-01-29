package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import matixar.mystockhub.API.*
import matixar.mystockhub.API.models.BestMatches
import matixar.mystockhub.API.models.GlobalQuote
import matixar.mystockhub.API.models.SearchResultModel
import matixar.mystockhub.API.models.StockApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class StockRepository(private val stockDao: StockDao) {

    val allStocks = MutableLiveData<List<SearchResultModel>>()
    val stockData = MutableLiveData<StockApiModel>()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(stock: Stock) {
        stockDao.insert(stock)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getStocksFromSearch(name: String) {
        val api = AlphaVantageApiInterface.create().searchStock(AlphaVantageFunctions.SEARCH.function, name, AlphaVantageApiInterface.API_KEY)
        api.enqueue(object : Callback<BestMatches> {
            override fun onResponse(call: Call<BestMatches>, response: Response<BestMatches>) {
                if(response.body()!!.results.isNotEmpty()) {
                    println("adding stocks")
                    allStocks.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<BestMatches>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getStockDataFromName(name: String) {
        val api = AlphaVantageApiInterface.create().getStock(AlphaVantageFunctions.STOCK_MODEL.function, name, AlphaVantageApiInterface.API_KEY)
        api.enqueue(object : Callback<GlobalQuote> {
            override fun onResponse(call: Call<GlobalQuote>, response: Response<GlobalQuote>) {
                stockData.value = response.body()!!.stockApiModel
            }

            override fun onFailure(call: Call<GlobalQuote>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun reloadFromDatabase() {
        //allStocks = stockDao.getAll()
    }

}