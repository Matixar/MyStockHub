package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class SingleCurrency(
    @SerializedName("mid") val mid: Float,
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("no") val no: String
)
