package matixar.mystockhub.util

import java.util.*

object StockOpenTime {

    fun isStockOpen(): Boolean {
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone("America/New_York")
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        return (hour > 9 || (hour == 9 && minute > 30)) && (hour < 16)
    }
}