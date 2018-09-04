package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import java.util.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun selectUntil(maxDate: Date, limit: Int = 100): Single<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE date = :date")
    fun selectAt(date: Date): Single<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workoutEntity: List<WorkoutEntity>)

//    @Transaction
//    fun insertAndSelect(today: Date): Single<List<WorkoutEntity>> {
//        val latest = getLatestWorkout()
//
//        if (latest.isEmpty() || latest.first().date > today) {
//            return Single.create<List<WorkoutEntity>> { emitter ->
//                emitter.onSuccess(select(today, 100))
//            }
//        }
//
//        val lastWorkoutDay = latest.first().date
//        val emptyWorkoutList = (lastWorkoutDay..today).iterator().asSequence().map {
//            WorkoutEntity(it, -1, 0)
//        }.toList()
//
//        insert(emptyWorkoutList)
//        return Single.create<List<WorkoutEntity>> { emitter ->
//            emitter.onSuccess(select(today, 100))
//        }
//    }
}