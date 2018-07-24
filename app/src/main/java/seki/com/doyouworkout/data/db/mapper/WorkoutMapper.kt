package seki.com.doyouworkout.data.db.mapper

import android.content.Context
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.Workout
import java.util.*
import javax.inject.Inject

class WorkoutMapper @Inject constructor(private val context: Context) {

    fun toWorkout(workoutEntities: List<WorkoutEntity>, trainingEntities: List<TrainingEntity>): List<Workout> {
        val trainingEntityMap: Map<Int, TrainingEntity> = trainingEntities.associateBy { it.id }

        val workoutList: MutableList<Workout> = mutableListOf()

        for (workoutEntity in workoutEntities) {
            val trainingEntity: TrainingEntity =
                    trainingEntityMap[workoutEntity.trainingId] ?: continue

            val trainingName: String =
                    if (trainingEntity.custom) {
                        trainingEntity.customName
                    }
                    else {
                        context.getString(trainingEntity.trainingNameId)
                    }

            val training = Workout(trainingEntity.id, trainingName, workoutEntity.count)
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

                val trainingName: String =
                        if (trainingEntity.custom) {
                            trainingEntity.customName
                        } else {
                            context.getString(trainingEntity.trainingNameId)
                        }

                trainingList += Workout(trainingEntity.id, trainingName, workout.count)
            }

            oneDayWorkoutList += OneDayWorkout(date, trainingList)
        }

        return oneDayWorkoutList
    }



    fun toWorkoutList(trainingEntities: List<TrainingEntity>): List<Workout> {
        fun TrainingEntity.toWorkout(): Workout {
            return Workout(
                    id = this.id,
                    name = if (this.custom) "" else context.getString(this.trainingNameId),
                    count = 0,
                    isUsed = this.used
            )
        }

        return trainingEntities.map { it.toWorkout() }
    }

    fun toTraining(entity: TrainingEntity) = entity.toData()

    private fun TrainingEntity.toData() =
            Training(
                    id = id,
                    trainingNameId = trainingNameId,
                    name = if (custom) "" else context.getString(trainingNameId),
                    isUsed = used,
                    isDeleted = delete,
                    isCustom = custom,
                    customName = customName)
}

