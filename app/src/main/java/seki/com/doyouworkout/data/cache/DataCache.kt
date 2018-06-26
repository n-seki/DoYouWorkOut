package seki.com.doyouworkout.data.cache

import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.Training
import java.util.*

class DataCache {
    private val workouts: MutableMap<Date, MutableList<WorkoutEntity>> = mutableMapOf()
    private val trainings: MutableMap<Int, TrainingEntity> = mutableMapOf()

    fun hasWorkoutAt(date: Date) = workouts.containsKey(date)

    fun hasTraining() = trainings.isNotEmpty()

    fun getWorkoutAt(date: Date) = workouts[date]

    fun getWorkoutFrom(maxDate: Date, limit: Int = 100) =
        workouts.filter { entry -> entry.key <= maxDate }
                .asIterable()
                .sortedByDescending { entry -> entry.key }
                .take(limit)

    fun getTraining(id: Int) = trainings[id]?.copy()

    fun getAllTraining() = trainings.values.map { it.copy() }

    fun putWorkout(workoutEntity: WorkoutEntity) {
        if (workouts.containsKey(workoutEntity.date)) {
            (workouts[workoutEntity.date] as MutableList) += workoutEntity
            return
        }
        workouts += workoutEntity.date to mutableListOf(workoutEntity)
    }

    fun updateTraining(trainingList: List<TrainingEntity>) {
        for (training in trainingList) {
            trainings[training.id] = training
        }
    }

    fun putTraining(trainingEntity: TrainingEntity) {
        trainings += trainingEntity.id to trainingEntity
    }

    fun clear() {
        workouts.clear()
        trainings.clear()
    }
}