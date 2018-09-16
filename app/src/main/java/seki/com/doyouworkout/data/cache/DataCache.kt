package seki.com.doyouworkout.data.cache

import io.reactivex.Single
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import java.util.*

open class DataCache: Cache {
    private val _workouts: MutableMap<Date, MutableList<WorkoutEntity>> = mutableMapOf()
    private val _trainings: MutableMap<Int, TrainingEntity> = mutableMapOf()

    override fun hasWorkoutAt(date: Date) = _workouts.containsKey(date)

    override fun hasTraining() = _trainings.isNotEmpty()

    override fun getWorkoutAt(date: Date): Single<List<WorkoutEntity>> = Single.just(_workouts[date])

    override fun getTraining(id: Int) = _trainings[id]

    override fun getAllTraining(): Single<List<TrainingEntity>> =
            Single.just(_trainings.values.asSequence().sortedBy { it.id }.toList())

    override fun putWorkout(workoutEntities: List<WorkoutEntity>) {
        _workouts += workoutEntities[0].date to workoutEntities.toMutableList()
    }

    override fun updateTraining(trainingList: List<TrainingEntity>) {
        for (training in trainingList) {
            _trainings[training.id] = training
        }
    }

    override fun putTraining(trainings: List<TrainingEntity>) {
        _trainings.putAll(trainings.associateBy { it.id })
    }

    override fun clear() {
        _workouts.clear()
        _trainings.clear()
    }
}