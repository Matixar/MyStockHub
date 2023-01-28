package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class SingleCurrencyList(
    @SerializedName("currency") val currency: String,
    @SerializedName("code") val code: String,
    @SerializedName("rates") val rates: List<SingleCurrency>
)
