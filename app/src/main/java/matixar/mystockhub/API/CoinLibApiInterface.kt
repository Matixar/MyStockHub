package matixar.mystockhub.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinLibApiInterface {

    @GET("coinlist")
    fun getCoinList(
        @Query("key") apiKey: String
    ): Call<CoinList>

    @GET("coin")
    fun getCoin(
        @Query("key") apiKey: String,
        @Query("symbol") symbol: String
    ): Call<Coin>

    companion object {

        var BASE_URL = "https://coinlib.io/api/v1/"
        var API_KEY = "089b25546d080347"

        fun create(): CoinLibApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CoinLibApiInterface::class.java)

        }
    }
}