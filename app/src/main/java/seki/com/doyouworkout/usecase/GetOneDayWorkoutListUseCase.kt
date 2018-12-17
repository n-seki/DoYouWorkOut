package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.ui.OneDayWorkout
import java.util.*

interface GetOneDayWorkoutListUseCase {
    fun execute(startExclusive: Date?): Single<List<OneDayWorkout>>
}