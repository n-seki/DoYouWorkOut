package seki.com.doyouworkout.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import java.util.*

interface LocalRepository {

    fun putDefaultTraining(): Completable
    fun insertTraining(trainingList: List<TrainingEntity>)
    fun selectTraining(): Single<List<TrainingEntity>>
    fun selectWorkoutAt(date: Date): Single<List<WorkoutEntity>>
    fun insertWorkout(workoutList: List<WorkoutEntity>)
    fun selectWorkoutUntil(date: Date, limit: Int): Single<List<WorkoutEntity>>
    fun isInitApp(): Single<Boolean>
}