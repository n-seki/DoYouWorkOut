package seki.com.doyouworkout.usecase

import io.reactivex.Single
import seki.com.doyouworkout.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingUseCase @Inject constructor(
        private val repository: Repository,
        private val scheduleProvider: SchedulersProviderBase)
{
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