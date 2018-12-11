package seki.com.doyouworkout.usecase.impl

import io.reactivex.Single
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.usecase.GetWorkoutUseCase
import java.util.*
import javax.inject.Inject

class GetWorkoutUseCaseImp @Inject constructor(
        private val repo: Repository,
        private val mapper: WorkoutMapper
): GetWorkoutUseCase {

    override fun execute(date: Date): Single<List<Workout>> {
        return repo.getWorkout(date)
                .flatMap { workoutList ->
                    repo.getAllTrainingList()
                        .map { mapper.toWorkout(workoutList, it) }
                }
    }
}