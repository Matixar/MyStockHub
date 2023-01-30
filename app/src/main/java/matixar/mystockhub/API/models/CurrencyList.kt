package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class CurrencyList(
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("rates") val rates: List<Currency>
)
