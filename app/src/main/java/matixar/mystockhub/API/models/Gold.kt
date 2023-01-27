package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class Gold(
    @SerializedName("data") val date: String,
    @SerializedName("cena") val price: Float
)
