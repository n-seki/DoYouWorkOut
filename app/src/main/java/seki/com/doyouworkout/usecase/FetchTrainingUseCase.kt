package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.ui.Training

interface FetchTrainingUseCase {
    fun execute(): Single<List<Training>>
}