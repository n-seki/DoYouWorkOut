package seki.com.doyouworkout.usecase.impl

import io.reactivex.Completable
import io.reactivex.Single
import seki.com.doyouworkout.data.*
import seki.com.doyouworkout.data.db.entity.TrainingEntity
import seki.com.doyouworkout.data.db.entity.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.resource.DateSupplier
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.usecase.GetOneDayWorkoutListUseCase
import java.util.*
import javax.inject.Inject

internal class GetOneDayWorkoutListUseCaseImp @Inject constructor(
        private val repository: Repository,
        private val mapper: WorkoutMapper,
        private val dateSupplier: DateSupplier
) : GetOneDayWorkoutListUseCase {
    override fun execute(startExclusive: Date?): Single<List<OneDayWorkout>> {
        if (startExclusive == null) {
            return fetchAndInsertOneDayWorkout(dateSupplier.getToday())
        }

        return fetchOneDayWorkoutList(startExclusive)
    }

    private fun fetchOneDayWorkoutList(today: Date): Single<List<OneDayWorkout>> {
        return repository.getWorkoutList(today, 100)
                .flatMap { workoutList ->
                    repository.getAllTrainingList()
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
    }

    private fun fetchAndInsertOneDayWorkout(today: Date): Single<List<OneDayWorkout>> {
        return repository.getLastWorkout()
                .flatMapCompletable { workout ->
                    if (!workout.date.equalsDay(today)) {
                        return@flatMapCompletable insertEmptyWorkoutData(workout.date)
                    }
                    return@flatMapCompletable Completable.complete()
                }
                .toSingle { fetchOneDayWorkoutList(today) }
                .flatMap { it }
    }

    private fun insertEmptyWorkoutData(lastDate: Date): Completable {
        return repository.getAllTrainingList()
                .map { createEmptyWorkoutList(
                        startDate = lastDate,
                        endDate = dateSupplier.getToday(),
                        trainingList = it)
                }
                .flatMapCompletable { repository.updateWorkout(it) }
    }

    private fun createEmptyWorkoutList(
            startDate: Date,
            endDate: Date,
            trainingList: List<TrainingEntity>
    ): List<WorkoutEntity> {
        return (startDate until endDate.ignoreTime()).iterator()
                .asSequence()
                .map { workoutDay ->
                    trainingList.map { training -> WorkoutEntity(workoutDay, training.id, 0) }
                }
                .toList()
                .flatten()
    }
}