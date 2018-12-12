package seki.com.doyouworkout.data.db.mapper

import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.Workout
import java.util.*

class WorkoutMapper {

    fun toWorkout(
            workoutEntities: List<WorkoutEntity>,
            trainingEntities: List<TrainingEntity>
    ): List<Workout> {
        val trainingEntityMap: Map<Int, TrainingEntity> = trainingEntities.associateBy { it.id }

        val workoutList: MutableList<Workout> = mutableListOf()

        for (workoutEntity in workoutEntities) {
            val trainingEntity: TrainingEntity =
                    trainingEntityMap[workoutEntity.trainingId] ?: continue

            val training = Workout(trainingEntity.id, trainingEntity.name, workoutEntity.count)
            workoutList += training
        }

        return workoutList
    }

    fun toOneDayWorkout(workoutEntities: List<WorkoutEntity>, trainingEntities: List<TrainingEntity>): List<OneDayWorkout> {
        val trainingEntityMap: Map<Int, TrainingEntity> = trainingEntities.associateBy { it.id }
        val workoutEntityMap: Map<Date, List<WorkoutEntity>> =
                workoutEntities.groupBy { it.date }

        val oneDayWorkoutList: MutableList<OneDayWorkout> = mutableListOf()

        for ((date, workoutList) in workoutEntityMap.entries) {
            val trainingList = mutableListOf<Workout>()
            for (workout in workoutList) {
                val trainingEntity: TrainingEntity =
                        trainingEntityMap[workout.trainingId] ?: continue

                trainingList += Workout(trainingEntity.id, trainingEntity.name, workout.count)
            }

            oneDayWorkoutList += OneDayWorkout(date, trainingList)
        }

        return oneDayWorkoutList
    }



    fun toWorkoutList(trainingEntities: List<TrainingEntity>): List<Workout> {
        fun TrainingEntity.toWorkout(): Workout {
            return Workout(
                    id = this.id,
                    name = this.name,
                    count = 0,
                    isUsed = this.used
            )
        }

        return trainingEntities.map { it.toWorkout() }
    }
}

fun TrainingEntity.toUIData() =
        Training(
                id = id,
                name = name,
                isUsed = used,
                isDeleted = delete,
                isCustom = custom
        )

