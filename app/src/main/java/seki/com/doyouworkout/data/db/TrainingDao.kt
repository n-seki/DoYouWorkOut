package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import seki.com.doyouworkout.data.db.entity.TrainingEntity

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training ORDER BY id")
    fun select(): Single<List<TrainingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingEntity>)
}