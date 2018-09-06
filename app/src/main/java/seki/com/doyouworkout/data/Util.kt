package seki.com.doyouworkout.data

import java.text.SimpleDateFormat
import java.util.*

operator fun ClosedRange<Date>.iterator(): Iterator<Date> {
    return DateIterator(this)
}

class DateIterator(private val dateRange: ClosedRange<Date>): Iterator<Date> {
    private var current = dateRange.start
    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

    override fun next(): Date {
        val result = current
        current = current.nextDay()
        return result
    }

    private fun Date.nextDay(): Date {
        val calendar = Calendar.getInstance().apply { time = this@nextDay }
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }
}

fun Date.equalsDay(date: Date): Boolean {
    return formatter.format(this) == formatter.format(date)
}

val formatter = SimpleDateFormat("yyyyMMdd", Locale.US)