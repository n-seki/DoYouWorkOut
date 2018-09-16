package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class TrainingUseCase @Inject constructor(
        private val repository: Repository, private val scheduleProvider: SchedulersProviderBase) {

    fun fetchTrainingList(): Single<List<Training>> =
            repository.getAllTrainingList()
                    .doOnSuccess { repository.putTrainingCache(it) }
                    .map { list -> list.map { it.toUIData() } }
                    .subscribeOn(scheduleProvider.io())
                    .observeOn(scheduleProvider.ui())

    fun updateTraining(list: List<Training>): Single<Boolean> =
            repository.updateTraining(list)
                    .subscribeOn(scheduleProvider.io())
                    .observeOn(scheduleProvider.ui())
                    .toSingleDefault(true)
                    .onErrorReturnItem(false)

    fun isCompleteInitApp(): Single<Boolean> =
            repository.isInitApp()
                    .subscribeOn(scheduleProvider.io())
                    .observeOn(scheduleProvider.ui())

    fun initApp() {
        repository.putDefaultTraining()
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe()
    }
}