package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import java.util.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun load(maxDate: Date, limit: Int = 100): List<WorkoutEntity>

    @Query("SELECT * FROM workout WHERE date = :date")
    fun select(date: Date): Single<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg workoutEntity: WorkoutEntity)

    @Delete
    fun delete(workoutEntity: WorkoutEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workoutEntity: WorkoutEntity)
}