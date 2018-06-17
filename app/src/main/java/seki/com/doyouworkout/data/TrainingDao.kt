package seki.com.doyouworkout.data

import android.arch.persistence.room.*

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training")
    fun loadAll(): List<TrainingEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(trainingEntity: TrainingEntity)

    @Update
    fun update(trainingEntity: TrainingEntity)
}