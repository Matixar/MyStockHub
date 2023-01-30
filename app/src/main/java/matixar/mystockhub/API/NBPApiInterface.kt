package matixar.mystockhub.API

import matixar.mystockhub.API.models.CurrencyList
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.models.SingleCurrencyList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NBPApiInterface {

    @GET("exchangerates/rates/A/{code}/")
    fun getCurrencyRate(
        @Path(value = "code", encoded = true) code: String,
        @Query("format") format: String = "json"
    ): Call<SingleCurrencyList>

    @GET("exchangerates/tables/A/last/2/")
    fun getCurrenciesExchangeRatesLast2Days(
        @Query("format") format: String = "json"
    ): Call<List<CurrencyList>>

    @GET("cenyzlota/")
    fun getGoldPriceToday(
        @Query("format") format: String = "json"
    ): Call<Gold>

    @GET("cenyzlota/last/2/")
    fun getGoldPrice2LastDays(
        @Query("format") format: String = "json"
    ): Call<List<Gold>>


    companion object {

        var BASE_URL = "http://api.nbp.pl/api/"

        fun create(): NBPApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NBPApiInterface::class.java)

        }
    }
}