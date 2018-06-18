package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout LIMIT :limit")
    fun load(limit: Int = 100): List<WorkoutEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(workoutEntity: WorkoutEntity)

    @Delete
    fun delete(workoutEntity: WorkoutEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workoutEntity: WorkoutEntity)
}