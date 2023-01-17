package matixar.mystockhub.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toCalendar(i: Long?): Calendar? {
        val c = Calendar.getInstance()
        if (i != null) {
            c.timeInMillis = i
        }
        return c
    }
    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.let { it.time.time }
    }
}