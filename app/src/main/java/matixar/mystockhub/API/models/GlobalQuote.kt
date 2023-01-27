package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class GlobalQuote(
    @SerializedName("Global Quote")
    val stockApiModel: StockApiModel
)
