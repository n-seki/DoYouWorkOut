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

    fun getWorkoutFrom(maxDate: Date, limit: Int = 100) =
        _workouts.filter { entry -> entry.key <= maxDate }
                .asIterable()
                .sortedByDescending { entry -> entry.key }
                .take(limit)

    fun getTraining(id: Int) = _trainings[id]?.copy()

    fun getAllTraining(): Flowable<List<TrainingEntity>> =
            Flowable.just(_trainings.values.asSequence().map { it.copy() }.toList())

    fun putWorkout(workoutEntity: WorkoutEntity) {
        if (_workouts.containsKey(workoutEntity.date)) {
            (_workouts[workoutEntity.date] as MutableList) += workoutEntity
            return
        }
        _workouts += workoutEntity.date to mutableListOf(workoutEntity)
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