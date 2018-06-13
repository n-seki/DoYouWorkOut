package seki.com.doyouworkout.data

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

@TypeConverter
fun fromStringToDate(dateString: String): Date {
    val dateFormat = SimpleDateFormat("yyyyMMDD", Locale.JAPAN)
    return dateFormat.parse(dateString)
}

@TypeConverter
fun fromDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyyMMDD", Locale.JAPAN)
    return dateFormat.format(date)
}