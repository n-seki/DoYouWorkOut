package seki.com.doyouworkout.usecase

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.data.repository.toEntity
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class TrainingUseCase @Inject constructor(
        private val repository: WorkoutRepository, private val workoutMapper: WorkoutMapper) {

    fun fetchTrainingList(): Flowable<List<Training>> =
            repository.getAllTrainingList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list -> list.map { workoutMapper.toTraining(it) } }
                .toFlowable()

    fun updateTraining(list: List<Training>): Flowable<Boolean> =
            repository.updateTraining(list.map { it.toEntity() })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toSingleDefault(true)
                    .onErrorReturnItem(false)
                    .toFlowable()
}