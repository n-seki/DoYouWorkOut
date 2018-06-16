package seki.com.doyouworkout.data

import android.arch.persistence.room.*

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training")
    fun loadAll(): List<Training>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(training: Training)

    @Update
    fun unuse(training: Training)
}