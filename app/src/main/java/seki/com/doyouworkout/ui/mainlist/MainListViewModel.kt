package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.TrainingUseCase
import seki.com.doyouworkout.usecase.WorkoutUseCase
import javax.inject.Inject

class MainListViewModel @Inject constructor(
        trainingUseCase: TrainingUseCase,
        workoutUseCase: WorkoutUseCase): ViewModel() {

    val initAppStatus: LiveData<Boolean> =
            trainingUseCase.isCompleteInitApp().toLiveData()

    val workoutList: LiveData<List<OneDayWorkout>> = workoutUseCase.fetchOneDayWorkoutList().toLiveData()


}