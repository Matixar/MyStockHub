package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("currency") val currencyName: String,
    @SerializedName("code") val code: String,
    @SerializedName("mid") val value: Float
)
