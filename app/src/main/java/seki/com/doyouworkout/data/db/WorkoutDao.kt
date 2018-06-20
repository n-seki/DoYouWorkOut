package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import java.util.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout WHERE date <= :maxDate LIMIT :limit")
    fun load(maxDate: Date, limit: Int = 100): List<WorkoutEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workoutEntity: WorkoutEntity)

    @Delete
    fun delete(workoutEntity: WorkoutEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workoutEntity: WorkoutEntity)
}