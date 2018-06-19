package seki.com.doyouworkout.data.cache

import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import java.util.*

class DataCache {
    private val workouts: MutableMap<Date, MutableList<WorkoutEntity>> = mutableMapOf()
    private val trainings: MutableMap<Int, TrainingEntity> = mutableMapOf()

    fun hasWorkoutAt(date: Date) = workouts.containsKey(date)
    fun hasTraining(id: Int) = trainings.containsKey(id)

    fun getWorkoutAt(date: Date) = workouts[date]
    fun getTraining(id: Int) = trainings[id]

    fun putWorkout(workoutEntity: WorkoutEntity) {
        if (workouts.containsKey(workoutEntity.date)) {
            (workouts[workoutEntity.date] as MutableList) += workoutEntity
            return
        }
        workouts += workoutEntity.date to mutableListOf(workoutEntity)
    }

    fun putTraining(trainingEntity: TrainingEntity) {
        trainings += trainingEntity.id to trainingEntity
    }

    fun clear() {
        workouts.clear()
        trainings.clear()
    }
}