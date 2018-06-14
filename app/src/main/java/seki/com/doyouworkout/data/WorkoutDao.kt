package seki.com.doyouworkout.data

import android.arch.persistence.room.*

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout LIMIT :limit")
    fun load(limit: Int = 100)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun intert(workout: Workout)

    @Delete
    fun delete(workout: Workout)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workout: Workout)
}