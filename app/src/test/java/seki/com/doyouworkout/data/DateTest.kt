package seki.com.doyouworkout.data

import com.google.common.truth.Truth.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

object DateTest : Spek({
    describe("Date extensions") {
        describe("ignoreTime") {
            it("時刻が0000のDateが作成されること") {
                val result = Date().ignoreTime()

                val format = SimpleDateFormat("yyyyMMddHHmmSS", Locale.US)
                val dateStr = format.format(result)

                assertThat(dateStr).endsWith("000000")
            }
        }

        describe("equalsDay") {
            val d1 = Date()
            val d2 = Date()

            context("同一日付") {
                it("日付が一致すること") {
                    assertThat(d1.equalsDay(d2))
                }
            }

            context("前日日付と比較") {
                val localDateTime = LocalDateTime.now().minusDays(1)
                val yesterday = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
                
                it("日付が不一致であること") {
                    assert(!d1.equalsDay(yesterday))
                }
            }
        }

        describe("previousDay") {
            it("前日日付のDateが取得できること") {
                val date = LocalDate.of(2018, 9, 22)
                val time = LocalTime.of(12, 55, 30)
                val dateTime = LocalDateTime.of(date, time)

                val targetDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
                val result = targetDate.previousDay()

                val resultDateTime = LocalDateTime.ofInstant(result.toInstant(), ZoneId.systemDefault())

                assertThat(resultDateTime.year).isEqualTo(date.year)
                assertThat(resultDateTime.month).isEqualTo(date.month)
                assertThat(resultDateTime.dayOfMonth).isEqualTo(date.dayOfMonth - 1)
                assertThat(resultDateTime.hour).isEqualTo(time.hour)
                assertThat(resultDateTime.minute).isEqualTo(time.minute)
                assertThat(resultDateTime.second).isEqualTo(time.second)
            }
        }
    }
})