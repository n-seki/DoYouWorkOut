package seki.com.doyouworkout.usecase.impl

import io.reactivex.Single
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.usecase.UpdateWorkoutUseCase
import java.util.*
import javax.inject.Inject

internal class UpdateWorkoutUseCaseImp @Inject constructor(
        private val repository: Repository
) : UpdateWorkoutUseCase {

    override fun execute(date: Date, workoutList: List<Workout>): Single<Boolean> {
        return repository.updateWorkout(workoutList.toTrainingEntity(date))
                .toSingleDefault(true)
                .onErrorReturnItem(false)
    }

    private fun List<Workout>.toTrainingEntity(date: Date): List<WorkoutEntity> {
        return this.map {
            WorkoutEntity(
                    date = date,
                    trainingId = it.id,
                    count = it.count
            )
        }
    }
}