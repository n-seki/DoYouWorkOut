package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.WorkoutUseCase
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(useCase: WorkoutUseCase): ViewModel() {

    val trainingList: LiveData<List<Workout>> =
            useCase.fetchEmptyWorkout().toLiveData()
}