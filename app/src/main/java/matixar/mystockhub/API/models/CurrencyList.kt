package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName
import matixar.mystockhub.API.models.Currency
import java.util.Date

data class CurrencyList(
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("rates") val rates: List<Currency>
)
