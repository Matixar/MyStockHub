package matixar.mystockhub.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import matixar.mystockhub.API.models.Coin
import java.util.*

@Entity
data class Crypto(
    @PrimaryKey(autoGenerate = true) val cryptoId: Long = 0,
    @ColumnInfo(name = "coin") val coin: Coin,
    @ColumnInfo(name = "purchase_date") val purchaseDate: Date,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "current_price") var currentPrice: Float
)
