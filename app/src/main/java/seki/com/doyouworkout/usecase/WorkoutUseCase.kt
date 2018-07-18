package seki.com.doyouworkout.usecase

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seki.com.doyouworkout.data.db.mapper.WorkoutMapper
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.Workout
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
        return repository.getAllTrainingList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { mapper.toWorkoutList(it) }
    }
}