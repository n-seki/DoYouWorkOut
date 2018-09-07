package seki.com.doyouworkout.usecase

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seki.com.doyouworkout.data.db.mapper.toUIData
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class TrainingUseCase @Inject constructor(private val repository: WorkoutRepository) {

    fun fetchTrainingList(): Single<List<Training>> =
            repository.getAllTrainingList()
                    .doOnSuccess { repository.putTrainingCache(it) }
                    .map { list -> list.map { it.toUIData() } }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun updateTraining(list: List<Training>): Single<Boolean> =
            repository.updateTraining(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toSingleDefault(true)
                    .onErrorReturnItem(false)

    fun isCompleteInitApp(): Single<Boolean> =
            repository.isInitApp()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun initApp() {
        repository.putDefaultTraining()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}