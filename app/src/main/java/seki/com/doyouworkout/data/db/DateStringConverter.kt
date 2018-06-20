package seki.com.doyouworkout.data.db

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateStringConverter {

    companion object {
        const val DATE_FORMAT: String = "yyyyMMDD"
    }

    @TypeConverter
    fun fromStringToDate(dateString: String): Date {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)
        return dateFormat.parse(dateString)
    }

    @TypeConverter
    fun fromDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)
        return dateFormat.format(date)
    }

}