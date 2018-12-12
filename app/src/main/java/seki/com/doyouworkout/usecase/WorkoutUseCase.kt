package seki.com.doyouworkout.usecase

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.equalsDay
import seki.com.doyouworkout.data.ignoreTime
import seki.com.doyouworkout.data.iterator
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.until
import seki.com.doyouworkout.ui.OneDayWorkout
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutUseCase @Inject constructor(
        private val repository: Repository,
        private val mapper: WorkoutMapper,
        private val schedulersProvider: SchedulersProviderBase
) {

    fun fetchOneDayWorkoutList(today: Date = Date()): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList(today, 100)
                .flatMap { workoutList ->
                    repository.getAllTrainingList()
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    private fun insertEmptyWorkoutData(lastDate: Date): Completable {
        return repository.getAllTrainingList()
                .flatMap { createEmptyWorkoutList(startDate = lastDate, trainingList = it) }
                .flatMapCompletable { repository.updateWorkout(it) }
                .subscribeOn(schedulersProvider.io())
    }

    fun createEmptyWorkoutList(
            startDate: Date,
            endDate: Date = Date(),
            trainingList: List<TrainingEntity>
    ): Single<List<WorkoutEntity>> =
            Single.just(
                    (startDate until endDate.ignoreTime()).iterator()
                            .asSequence()
                            .map { workoutDay ->
                                trainingList.map { training -> WorkoutEntity(workoutDay, training.id, 0) }
                            }
                            .toList()
                            .flatten()
        )

    fun fetchAndInsertOneDayWorkout(today: Date = Date()): Single<List<OneDayWorkout>> {
        return repository.getLastWorkout()
                .flatMapCompletable { workout ->
                    if (!workout.date.equalsDay(today)) {
                        return@flatMapCompletable insertEmptyWorkoutData(workout.date)
                    }
                    return@flatMapCompletable Completable.complete()
                }
                .toSingle { fetchOneDayWorkoutList() }
                .flatMap { it }
                .subscribeOn(schedulersProvider.io())
    }
}