package seki.com.doyouworkout.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Training::class, Workout::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun workoutDao(): WorkoutDao
}