package matixar.mystockhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Stock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "stock_name") val stockName: String?,
    @ColumnInfo(name = "search_string") val searchString: String?,
    @ColumnInfo(name = "current_value") var currentValue: Float?,
    @ColumnInfo(name = "data_date") var dataDate: Calendar,
    @ColumnInfo(name = "previous_value") var previousValue: Float?

)
