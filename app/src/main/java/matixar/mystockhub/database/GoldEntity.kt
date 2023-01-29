package matixar.mystockhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import matixar.mystockhub.API.models.Gold
import java.util.Date

@Entity
data class GoldEntity(
    @PrimaryKey(autoGenerate = true) val goldId: Long = 0,
    @ColumnInfo(name = "gold") val gold: Gold,
    @ColumnInfo(name = "purchase_date") val purchaseDate: Date,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "current_price") var currentPrice: Float
)
