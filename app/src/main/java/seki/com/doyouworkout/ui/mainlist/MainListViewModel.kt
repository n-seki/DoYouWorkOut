package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.TrainingUseCase
import javax.inject.Inject

class MainListViewModel @Inject constructor(
        private val trainingUseCase: TrainingUseCase): ViewModel() {


}