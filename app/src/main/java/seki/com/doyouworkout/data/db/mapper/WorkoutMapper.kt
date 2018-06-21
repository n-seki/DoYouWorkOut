package seki.com.doyouworkout.data.db.mapper

import android.content.Context
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.ui.mainlist.Training
import seki.com.doyouworkout.ui.mainlist.Workout
import javax.inject.Inject

class WorkoutMapper @Inject constructor(private val context: Context) {

    fun toWorkout(workoutEntities: List<WorkoutEntity>, trainingEntities: List<TrainingEntity>): Workout {
        val trainingEntityMap: Map<Int, TrainingEntity> = trainingEntities.associateBy { it.id }

        val trainingList: MutableList<Training> = mutableListOf()

        for (workoutEntity in workoutEntities) {
            val trainingEntity: TrainingEntity =
                    trainingEntityMap[workoutEntity.trainingId] ?: continue

            val trainingName: String =
                    if (trainingEntity.isCustom) trainingEntity.customName
                    else context.getString(trainingEntity.trainingNameId)

            val training = Training(trainingEntity.id, trainingName, workoutEntity.count)
            trainingList += training
        }

        return Workout(workoutEntities[0].date, trainingList)
    }
}