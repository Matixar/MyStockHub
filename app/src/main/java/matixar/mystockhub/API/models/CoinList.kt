package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class CoinList(
    @SerializedName("coins") val coins: List<Coin>
)
