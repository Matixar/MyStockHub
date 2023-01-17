package matixar.mystockhub.API

import com.google.gson.annotations.SerializedName

data class GlobalQuote(
    @SerializedName("Global Quote")
    val stockApiModel: StockApiModel
)
