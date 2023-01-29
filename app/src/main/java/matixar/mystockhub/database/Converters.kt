package matixar.mystockhub.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import matixar.mystockhub.API.models.Coin
import matixar.mystockhub.API.models.Gold
import matixar.mystockhub.API.models.StockApiModel
import java.util.*

class Converters {
    val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromCoin(coin: Coin): String {
        return gson.toJson(coin)
    }

    @TypeConverter
    fun toCoin(data: String): Coin {
        return gson.fromJson(data,Coin::class.java)
    }

    @TypeConverter
    fun fromGold(gold: Gold): String {
        return gson.toJson(gold)
    }

    @TypeConverter
    fun toGold(data: String): Gold {
        return gson.fromJson(data,Gold::class.java)
    }

    @TypeConverter
    fun fromStockApiModel(stockApiModel: StockApiModel): String {
        return gson.toJson(stockApiModel)
    }

    @TypeConverter
    fun toStockApiModel(data: String): StockApiModel {
        return gson.fromJson(data, StockApiModel::class.java)
    }
}