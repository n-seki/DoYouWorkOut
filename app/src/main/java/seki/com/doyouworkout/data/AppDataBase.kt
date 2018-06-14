package seki.com.doyouworkout.data

import android.arch.persistence.room.Database

@Database(entities = [Training::class, Workout::class], version = 1)
abstract class AppDataBase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun workoutDao(): WorkoutDao
}