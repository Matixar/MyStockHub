package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName
import matixar.mystockhub.API.models.Coin

data class CoinList(
    @SerializedName("coins") val coins: List<Coin>
)
