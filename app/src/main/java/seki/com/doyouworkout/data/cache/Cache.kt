package seki.com.doyouworkout.data.cache

import io.reactivex.Single
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import java.util.*

interface Cache {

    fun hasWorkoutAt(date: Date): Boolean
    fun hasTraining(): Boolean
    fun getWorkoutAt(date: Date): Single<List<WorkoutEntity>>
    fun getTraining(id: Int): TrainingEntity?
    fun getAllTraining(): Single<List<TrainingEntity>>
    fun putWorkout(workoutEntities: List<WorkoutEntity>)
    fun updateTraining(trainingList: List<TrainingEntity>)
    fun putTraining(trainings: List<TrainingEntity>)
    fun clear()
}