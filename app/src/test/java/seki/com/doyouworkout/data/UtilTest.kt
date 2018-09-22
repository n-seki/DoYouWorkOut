package seki.com.doyouworkout.data

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class UtilTest {

    @Test
    fun `ignoreTimeで時刻が0000のDateが作成されること`() {
        val result = Date().ignoreTime()

        val format = SimpleDateFormat("yyyyMMddHHmmSS", Locale.US)
        val dateStr = format.format(result)

        assertThat(dateStr.substring(8), `is`("000000"))
    }

    @Test
    fun `Dateの比較ができること`() {
        val d1 = Date()
        val d2 = Date()
        assert(d1.equalsDay(d2))

        val localDateTime = LocalDateTime.now().minusDays(1)

        val yesterday = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        assert(!d1.equalsDay(yesterday))
    }

    @Test
    fun `前日日付のDateが取得できること`() {
        val date = LocalDate.of(2018, 9, 22)
        val time = LocalTime.of(12, 55, 30)
        val dateTime = LocalDateTime.of(date, time)

        val targetDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
        val result = targetDate.previousDay()

        val resultDateTime = LocalDateTime.ofInstant(result.toInstant(), ZoneId.systemDefault())

        assertThat(resultDateTime.year, `is`(date.year))
        assertThat(resultDateTime.month, `is`(date.month))
        assertThat(resultDateTime.dayOfMonth, `is`(date.dayOfMonth - 1))
        assertThat(resultDateTime.hour, `is`(time.hour))
        assertThat(resultDateTime.minute, `is`(time.minute))
        assertThat(resultDateTime.second, `is`(time.second))
    }
}