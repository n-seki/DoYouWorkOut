package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import java.util.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun selectUntil(maxDate: Date, limit: Int = 100): Single<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE date = :date")
    fun selectAt(date: Date): Single<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workoutEntity: List<WorkoutEntity>)
}