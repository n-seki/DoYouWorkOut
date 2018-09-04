package seki.com.doyouworkout.data.cache

import io.reactivex.Flowable
import io.reactivex.Single
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import java.util.*

class DataCache {
    private val _workouts: MutableMap<Date, MutableList<WorkoutEntity>> = mutableMapOf()
    private val _trainings: MutableMap<Int, TrainingEntity> = mutableMapOf()

    fun hasWorkoutAt(date: Date) = _workouts.containsKey(date)

    fun hasTraining() = _trainings.isNotEmpty()

    fun getWorkoutAt(date: Date): Single<List<WorkoutEntity>> = Single.just(_workouts[date])

    fun getTraining(id: Int) = _trainings[id]

    fun getAllTraining(): Single<List<TrainingEntity>> =
            Single.just(_trainings.values.asSequence().sortedBy { it.id }.toList())

    fun putWorkout(workoutEntities: List<WorkoutEntity>) {
        _workouts += workoutEntities[0].date to workoutEntities.toMutableList()
    }

    fun updateTraining(trainingList: List<TrainingEntity>) {
        for (training in trainingList) {
            _trainings[training.id] = training
        }
    }

    fun putTraining(trainings: List<TrainingEntity>) {
        _trainings.putAll(trainings.associateBy { it.id })
    }

    fun clear() {
        _workouts.clear()
        _trainings.clear()
    }
}