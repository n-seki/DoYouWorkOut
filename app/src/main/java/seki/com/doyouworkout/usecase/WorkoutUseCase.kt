package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Single
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

    fun getWorkout(date: Date): Single<List<Workout>> {
        return repository.getWorkout(date)
                .subscribeOn(Schedulers.io())
                .flatMap { workoutList -> repository.getAllTrainingList()
                            .map { mapper.toWorkout(workoutList, it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchEmptyWorkout(): Single<List<Workout>> {
        return repository.getUsedTrainingList()
                .map { mapper.toWorkoutList(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchOneDayWorkoutList(): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList()
                .subscribeOn(Schedulers.io())
                .flatMap { workoutList ->
                    repository.getAllTrainingList()
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    fun aaa(): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList(1)
                .map { list -> list[0].date } // TODO [Date?]
                .doOnSuccess { date ->
                    if (!date.equalsDay(Date())) {
                        insertEmptyWorkoutData(date)
                    }
                }
                .subscribeOn(Schedulers.io())
                .flatMap { fetchOneDayWorkoutList() }
    }

    fun updateWorkout(date: Date, workoutList: List<Workout>): Single<Boolean> {
        return repository.updateWorkout(workoutList.toTrainingEntity(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toSingleDefault(true)
                .onErrorReturnItem(false)
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