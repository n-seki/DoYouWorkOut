package seki.com.doyouworkout.usecase.impl

import io.reactivex.Single
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.usecase.FetchTrainingUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FetchTrainingUseCaseImp @Inject constructor(
        private val repository: Repository
): FetchTrainingUseCase {
    override fun execute(): Single<List<Training>> {
        return repository.getAllTrainingList()
                .doOnSuccess { repository.putTrainingCache(it) }
                .map { list -> list.map { it.toUIData() }}
    }
}