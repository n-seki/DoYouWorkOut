package seki.com.doyouworkout.usecase.impl

import io.reactivex.Single
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.usecase.UpdateTrainingUseCase
import javax.inject.Inject

internal class UpdateTrainingUseCaseImp @Inject constructor(
        private val repository: Repository
) : UpdateTrainingUseCase {
    override fun execute(list: List<Training>): Single<Boolean> {
        return repository.updateTraining(list)
                .toSingleDefault(true)
                .onErrorReturnItem(false)
    }
}