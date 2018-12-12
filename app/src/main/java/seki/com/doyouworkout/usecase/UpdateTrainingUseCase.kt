package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.ui.Training

interface UpdateTrainingUseCase {
    fun execute(list: List<Training>): Single<Boolean>
}