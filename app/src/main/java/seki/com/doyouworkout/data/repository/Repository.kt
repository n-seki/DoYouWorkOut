package seki.com.doyouworkout.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

interface Repository {
    fun isInitApp(): Single<Boolean>
    fun putDefaultTraining(): Completable
    fun getAllTrainingList(): Single<List<TrainingEntity>>
    fun getUsedTrainingList(): Single<List<TrainingEntity>>
    fun putTrainingCache(trainingList: List<TrainingEntity>)
    fun updateTraining(trainingList: List<Training>): Completable
    fun getWorkout(date: Date): Single<List<WorkoutEntity>>
    fun updateWorkout(workoutEntities: List<WorkoutEntity>): Completable
    fun getWorkoutList(limit: Int): Single<List<WorkoutEntity>>
}