package matixar.mystockhub.API

import com.google.gson.annotations.SerializedName

data class CoinList(
    @SerializedName("coins") val coins: List<Coin>
)
