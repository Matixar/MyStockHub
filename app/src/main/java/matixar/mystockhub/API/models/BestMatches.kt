package matixar.mystockhub.API.models

import com.google.gson.annotations.SerializedName

data class BestMatches(
    @SerializedName("bestMatches") val results: List<SearchResultModel>
)
