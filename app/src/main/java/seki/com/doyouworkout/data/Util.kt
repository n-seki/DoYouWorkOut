package seki.com.doyouworkout.data

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

operator fun ClosedRange<Date>.iterator(): Iterator<Date> {
    return DateIterator(this)
}

class DateIterator(private val dateRange: ClosedRange<Date>): Iterator<Date> {
    private var current = dateRange.start.nextDay().ignoreTime()
    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

    override fun next(): Date {
        val result = current
        current = current.nextDay().ignoreTime()
        return result
    }

    private fun Date.nextDay(): Date {
        val yesterday = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault()).plusDays(1)
        return Date.from(yesterday.atZone(ZoneId.systemDefault()).toInstant())
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
    val modifyDate = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

    return Date.from(modifyDate.atZone(ZoneId.systemDefault()).toInstant())
}

fun Date.previousDay(): Date {
    val yesterday = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault()).minusDays(1)
    return Date.from(yesterday.atZone(ZoneId.systemDefault()).toInstant())
}

val formatter = SimpleDateFormat("yyyyMMdd", Locale.US)