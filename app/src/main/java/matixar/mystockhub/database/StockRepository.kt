package matixar.mystockhub.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import matixar.mystockhub.API.AlphaVantageApiInterface
import matixar.mystockhub.API.AlphaVantageFunctions
import matixar.mystockhub.API.BestMatches
import matixar.mystockhub.API.GlobalQuote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class StockRepository(private val stockDao: StockDao) {

    val allStocks =  MutableLiveData<List<Stock>>()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(stock: Stock) {
        stockDao.insert(stock)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertOrUpdateFromApi(symbol: String) {
        val api = AlphaVantageApiInterface.create().getStock(AlphaVantageFunctions.STOCK_MODEL.function, symbol, AlphaVantageApiInterface.API_KEY)
        api.enqueue(object : Callback<GlobalQuote> {
            override fun onResponse(call: Call<GlobalQuote>, response: Response<GlobalQuote>) {
                val stockApiModel = response.body()?.stockApiModel
                val cal = Calendar.getInstance()
                cal.time = SimpleDateFormat("YYYY-MM-dd").parse(stockApiModel?.latestTradingDay!!)!!
                val stock = Stock(stockName = stockApiModel.symbol, searchString = stockApiModel.symbol, dataDate = cal, currentValue = stockApiModel.price, previousValue = stockApiModel.previousClose)
                if(stockDao.getStock(symbol) == null)
                    stockDao.insertAll(stock)
                else {
                    val dbStock = stockDao.getStock(symbol)
                    dbStock!!.dataDate = cal
                    dbStock.currentValue = stock.currentValue
                    dbStock.previousValue = stock.previousValue
                    stockDao.insertAll(dbStock)
                }

            }

            override fun onFailure(call: Call<GlobalQuote>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getStocksFromSearch(name: String) {
        val api = AlphaVantageApiInterface.create().searchStock(AlphaVantageFunctions.SEARCH.function, name, AlphaVantageApiInterface.API_KEY)
        api.enqueue(object : Callback<BestMatches> {
            override fun onResponse(call: Call<BestMatches>, response: Response<BestMatches>) {
                if(response.body()!!.results.isNotEmpty()) {
                    val list = mutableListOf<Stock>()
                    for (res in response.body()!!.results) {
                        list.add(
                            Stock(
                                stockName = res.name,
                                searchString = res.symbol,
                                currentValue = 0F,
                                previousValue = 0F,
                                dataDate = Calendar.getInstance()
                            )
                        )
                        //if(stockDao.getStock(list.last().searchString!!) == null)
                           // stockDao.insertAll(list.last())
                    }
                        //val flow = flowOf(list)
                    println("adding stocks")
                    allStocks.value = list
                    //allStocks = flow
                    println(list.toString())
                }
            }

            override fun onFailure(call: Call<BestMatches>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun reloadFromDatabase() {
        //allStocks = stockDao.getAll()
    }

}