package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import java.util.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun load(maxDate: Date, limit: Int = 100): Single<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE date = :date")
    fun select(date: Date): Single<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workoutEntity: List<WorkoutEntity>)

    @Delete
    fun delete(workoutEntity: WorkoutEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workoutEntity: WorkoutEntity)

    @Query("SELECT * FROM workout LIMIT 1")
    fun getLatestWorkout(): List<WorkoutEntity>

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun select(maxDate: Date, limit: Int = 100): List<WorkoutEntity>

    @Transaction
    fun insertAndSelect(today: Date): Single<List<WorkoutEntity>> {
        val latest = getLatestWorkout()

        if (latest.isEmpty() || latest.first().date > today) {
            return Single.create<List<WorkoutEntity>> { emitter ->
                emitter.onSuccess(select(today, 100))
            }
        }

        val lastWorkoutDay = latest.first().date
        val emptyWorkoutList = (lastWorkoutDay..today).iterator().asSequence().map {
            WorkoutEntity(it, -1, 0)
        }.toList()

        insert(emptyWorkoutList)
        return Single.create<List<WorkoutEntity>> { emitter ->
            emitter.onSuccess(select(today, 100))
        }
    }

    private operator fun ClosedRange<Date>.iterator(): Iterator<Date> {
        return DateIterator(this)
    }

    private class DateIterator(private val dateRange: ClosedRange<Date>): Iterator<Date> {
        var current = dateRange.start
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
}