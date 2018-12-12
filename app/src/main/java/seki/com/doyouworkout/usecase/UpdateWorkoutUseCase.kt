package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.ui.Workout
import java.util.*

interface UpdateWorkoutUseCase {
    fun execute(date: Date, workoutList: List<Workout>): Single<Boolean>
}