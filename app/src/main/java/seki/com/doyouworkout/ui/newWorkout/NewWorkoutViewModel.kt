package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.WorkoutUseCase
import java.util.*
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(private val useCase: WorkoutUseCase): ViewModel() {

    val trainingList: LiveData<List<Workout>> =
            useCase.fetchEmptyWorkout().toLiveData()

    private val _workoutList: MutableLiveData<List<Workout>> = MutableLiveData()
    val updateStatus: LiveData<Boolean>

    init {
        updateStatus = Transformations.switchMap(_workoutList) {
            updateWorkout(it)
        }
    }

    fun update(workoutList: List<Workout>) {
        _workoutList.postValue(workoutList)
    }

    private fun updateWorkout(workoutList: List<Workout>): LiveData<Boolean> {
        return useCase.updateWorkout(Date(), workoutList).toLiveData()
    }
}