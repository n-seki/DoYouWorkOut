package seki.com.doyouworkout.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [TrainingEntity::class, WorkoutEntity::class], version = 1)
@TypeConverters(value = [TypeConverters::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun workoutDao(): WorkoutDao
}