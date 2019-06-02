package seki.com.doyouworkout.usecase

import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.di.WorkoutRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingUseCase @Inject constructor(
        @WorkoutRepository private val repository: Repository,
        private val scheduleProvider: SchedulersProviderBase
) {
    fun isCompleteInitApp(): Boolean = repository.isInitApp()

    fun initApp() {
        repository.putDefaultTraining()
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe()
    }
}