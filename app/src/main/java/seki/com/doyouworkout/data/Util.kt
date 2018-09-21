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

infix fun Date.until(to: Date): ClosedRange<Date> {
    return object : ClosedRange<Date> {
        override val endInclusive: Date
            get() = to.previousDay().ignoreTime()
        override val start: Date
            get() = this@until
    }
}

fun Date.equalsDay(date: Date): Boolean {
    return formatter.format(this) == formatter.format(date)
}

fun Date.ignoreTime(): Date {
    val format = SimpleDateFormat("yyyyMMDD", Locale.US)
    return format.parse(format.format(this))!!
}

fun Date.previousDay(): Date {
    val calendar = Calendar.getInstance().apply { time = this@previousDay }
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return calendar.time
}

val formatter = SimpleDateFormat("yyyyMMdd", Locale.US)