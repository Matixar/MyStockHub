package matixar.mystockhub.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApiInterface {

    @GET("/query")
    fun getStock(
        @Query("function") function: String, @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Call<GlobalQuote>

    @GET("/query")
    fun searchStock(
        @Query("function") function: String, @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): Call<BestMatches>

    companion object {

        var BASE_URL = "https://www.alphavantage.co"
        var API_KEY = "XKG7QY4X9NCFVQEH"

        fun create(): AlphaVantageApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(AlphaVantageApiInterface::class.java)

        }
    }
}