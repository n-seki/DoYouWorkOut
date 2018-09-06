package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.data.equalsDay
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.TrainingUseCase
import seki.com.doyouworkout.usecase.WorkoutUseCase
import java.util.*
import javax.inject.Inject

class MainListViewModel @Inject constructor(
        trainingUseCase: TrainingUseCase,
        workoutUseCase: WorkoutUseCase): ViewModel() {

    val initAppStatus: LiveData<Boolean> =
            trainingUseCase.isCompleteInitApp().toLiveData()

    val workoutList: LiveData<List<OneDayWorkout>> = workoutUseCase.fetchOneDayWorkoutList().toLiveData()

    val hasTodayWorkout: LiveData<Boolean> = Transformations.switchMap(workoutList) { list ->
        containsTodayWorkout(list)
    }

    private fun containsTodayWorkout(workoutList: List<OneDayWorkout>): LiveData<Boolean> {
        val isContainTodayWorkout = MutableLiveData<Boolean>()

        if (workoutList.isEmpty()) {
            isContainTodayWorkout.postValue(false)
            return isContainTodayWorkout
        }

        isContainTodayWorkout.postValue(workoutList.last().trainingDate.equalsDay(Date()))
        return isContainTodayWorkout
    }
}