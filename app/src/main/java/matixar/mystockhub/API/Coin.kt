package matixar.mystockhub.API

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coin(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("show_symbol") val showSymbol : String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("market_cap") val marketCap: String,
    @SerializedName("delta_24h") val delta24h: String
) : Serializable
