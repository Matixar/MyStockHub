package matixar.mystockhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Crypto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "crypto_name") val currencyName: String?,
    @ColumnInfo(name = "search_string") val searchString: String?,
    @ColumnInfo(name = "current_value") val currentValue: Float?,
    @ColumnInfo(name = "data_date") val dataDate: Calendar,
    @ColumnInfo(name = "previous_value") val previousValue: Float?
)
