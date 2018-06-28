package seki.com.doyouworkout.data.db

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training")
    fun loadAll(): Single<List<TrainingEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(list: List<TrainingEntity>)

    @Update
    fun update(list: List<TrainingEntity>)

    @Query("SELECT COUNT(*) FROM training")
    fun count(): Int
}