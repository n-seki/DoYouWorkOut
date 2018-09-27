package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import seki.com.doyouworkout.data.db.dao.TrainingDao
import seki.com.doyouworkout.data.db.dao.WorkoutDao
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity

@Database(entities = [TrainingEntity::class, WorkoutEntity::class], version = 1)
@TypeConverters(value = [DateStringConverter::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun workoutDao(): WorkoutDao
}