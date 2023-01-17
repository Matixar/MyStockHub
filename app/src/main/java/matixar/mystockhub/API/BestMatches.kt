package matixar.mystockhub.API

import com.google.gson.annotations.SerializedName

data class BestMatches(
    @SerializedName("bestMatches") val results: List<SearchResultModel>
)
