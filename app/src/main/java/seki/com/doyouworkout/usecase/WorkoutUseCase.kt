package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.db.TrainingEntity
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.equalsDay
import seki.com.doyouworkout.data.ignoreTime
import seki.com.doyouworkout.data.iterator
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.until
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import java.util.*
import javax.inject.Inject

class WorkoutUseCase
@Inject constructor(
        private val repository: Repository,
        private val mapper: WorkoutMapper,
        private val schedulersProvider: SchedulersProviderBase) {

    fun getWorkout(date: Date): Single<List<Workout>> {
        return repository.getWorkout(date)
                .subscribeOn(schedulersProvider.io())
                .flatMap { workoutList -> repository.getAllTrainingList()
                            .map { mapper.toWorkout(workoutList, it) }
                }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    fun fetchEmptyWorkout(): Single<List<Workout>> {
        return repository.getUsedTrainingList()
                .map { mapper.toWorkoutList(it) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    fun fetchOneDayWorkoutList(): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList(100)
                .subscribeOn(schedulersProvider.io())
                .flatMap { workoutList ->
                    repository.getAllTrainingList()
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    private fun insertEmptyWorkoutData(lastDate: Date): Completable {
        return repository.getAllTrainingList()
                .flatMap { createEmptyWorkoutList(lastDate, it) }
                .flatMapCompletable { repository.updateWorkout(it) }
                .subscribeOn(schedulersProvider.io())
    }

    fun createEmptyWorkoutList(date: Date, trainingList: List<TrainingEntity>): Single<List<WorkoutEntity>> {
        return Single.just(
                    (date until Date().ignoreTime()).iterator()
                            .asSequence()
                            .map { workoutDay ->
                                trainingList.map { training -> WorkoutEntity(workoutDay, training.id, 0) }
                            }
                            .toList()
                            .flatten()
        )
    }

    fun aaa(): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList(1)
                .map { list -> list[0].date } // TODO [Date?]
                .flatMapCompletable { date ->
                    if (!date.equalsDay(Date())) {
                        return@flatMapCompletable insertEmptyWorkoutData(date)
                    }
                    return@flatMapCompletable Completable.complete()
                }
                .toSingle { fetchOneDayWorkoutList() }
                .flatMap { it }
                .subscribeOn(schedulersProvider.io())
    }

    fun updateWorkout(date: Date, workoutList: List<Workout>): Single<Boolean> {
        return repository.updateWorkout(workoutList.toTrainingEntity(date))
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
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