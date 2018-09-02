package seki.com.doyouworkout.usecase

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seki.com.doyouworkout.data.db.WorkoutEntity
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.ui.mainlist.MainListViewModel
import java.util.*
import javax.inject.Inject

class WorkoutUseCase
@Inject constructor(private val repository: WorkoutRepository, private val mapper: WorkoutMapper) {

    fun getWorkout(date: Date): Flowable<List<Workout>> {
        return repository.getWorkout(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapPublisher { workoutList ->
                    repository.getAllTrainingList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { mapper.toWorkout(workoutList, it) }
                }
    }

    fun fetchEmptyWorkout(): Flowable<List<Workout>> {
        return repository.getUsedTrainingList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { mapper.toWorkoutList(it) }
    }

    fun fetchOneDayWorkoutList(): Flowable<List<OneDayWorkout>> {
        return repository.getWorkoutList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapPublisher { workoutList ->
                    repository.getAllTrainingList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
    }

    fun fetchOneDayWorkoutListWith(): Flowable<List<OneDayWorkout>> {
        return repository.getWorkoutList(Date())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapPublisher { workoutList ->
                    repository.getAllTrainingList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { mapper.toOneDayWorkout(workoutList, it) }
                }
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