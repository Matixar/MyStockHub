package matixar.mystockhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import matixar.mystockhub.API.models.StockApiModel
import java.util.*

@Entity
data class Stock(
    @PrimaryKey(autoGenerate = true) val stockId: Long = 0,
    @ColumnInfo(name = "stock") val stockApiModel: StockApiModel,
    @ColumnInfo(name = "purchase_date") val purchaseDate: Date,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "current_price") var currentPrice: Float
)
