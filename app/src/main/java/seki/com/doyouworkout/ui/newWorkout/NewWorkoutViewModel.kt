package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.TrainingUseCase
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(private val useCase: TrainingUseCase): ViewModel() {

    val trainingList: LiveData<List<Training>> =
            useCase.fetchUsedTrainingList().toLiveData()
}