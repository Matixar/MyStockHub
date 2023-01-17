package matixar.mystockhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Currency(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "currency_name") val currencyName: String?,
    @ColumnInfo(name = "search_string") val searchString: String?,
    @ColumnInfo(name = "current_rate") val currentRate: Float?,
    @ColumnInfo(name = "data_date") val dataDate: Calendar,
    @ColumnInfo(name = "previous_value") val previousValue: Float?,
    @ColumnInfo(name = "rate_in_dollars") val rateInDollars: Float?
)
