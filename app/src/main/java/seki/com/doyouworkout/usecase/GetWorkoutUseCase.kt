package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.ui.Workout
import java.util.*

interface GetWorkoutUseCase {
    fun execute(date: Date): Single<List<Workout>>
}