package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.equalsDay
import seki.com.doyouworkout.data.iterator
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import java.util.*
import javax.inject.Inject

class WorkoutUseCase
@Inject constructor(private val repository: WorkoutRepository, private val mapper: WorkoutMapper) {

    fun getWorkout(date: Date): Flowable<List<Workout>> {
        return repository.getWorkout(date)
                .subscribeOn(Schedulers.io())
                .flatMap { workoutList -> repository.getAllTrainingList()
                            .map { mapper.toWorkout(workoutList, it) }
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
    }

    fun fetchEmptyWorkout(): Flowable<List<Workout>> {
        return repository.getUsedTrainingList()
                .map { mapper.toWorkoutList(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
    }

    fun fetchOneDayWorkoutList(): Flowable<List<OneDayWorkout>> {
        return repository.getWorkoutList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { workoutList ->
                    repository.getAllTrainingList()
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
    }

    private fun insertEmptyWorkoutData(lastDate: Date): Completable {
        return repository.getAllTrainingList()
                .map { insertEmptyWorkoutDataUntil(lastDate, it) }
                .ignoreElement()
    }

    private fun insertEmptyWorkoutDataUntil(date: Date, trainingList: List<TrainingEntity>): Completable {
        return Completable.fromAction {
            val emptyWorkoutList: List<WorkoutEntity> =
                    (date..Date()).iterator()
                            .asSequence()
                            .map { workout ->
                                trainingList.map { training -> WorkoutEntity(workout, training.id, 0) }
                            }
                            .toList()
                            .flatten()

            repository.updateWorkout(emptyWorkoutList)
        }
    }

    fun aaa(): Flowable<List<OneDayWorkout>> {
        return repository.getWorkoutList(1)
                .map { list -> list[0].date } // TODO [Date?]
                .doOnSuccess { date ->
                    if (!date.equalsDay(Date())) {
                        insertEmptyWorkoutData(date)
                    }
                }
                .subscribeOn(Schedulers.io())
                .flatMapPublisher { fetchOneDayWorkoutList() }
    }

    fun updateWorkout(date: Date, workoutList: List<Workout>): Flowable<Boolean> {
        return repository.updateWorkout(workoutList.toTrainingEntity(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toSingleDefault(true)
                .onErrorReturnItem(false)
                .toFlowable()
    }


    private fun List<Workout>.toTrainingEntity(date: Date): List<WorkoutEntity> {
        return this.map {
            WorkoutEntity(
                    date = date,
                    trainingId = it.id,
                    count = it.count
            )
        }
    }
}