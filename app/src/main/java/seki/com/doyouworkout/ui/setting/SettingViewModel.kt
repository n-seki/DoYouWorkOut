package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.FetchTrainingUseCase
import seki.com.doyouworkout.usecase.SchedulersProviderBase
import seki.com.doyouworkout.usecase.TrainingUseCase
import javax.inject.Inject

class SettingViewModel @Inject constructor(
        private val useCase: TrainingUseCase,
        fetchTrainingUseCase: FetchTrainingUseCase,
        schedulersProvider: SchedulersProviderBase
): ViewModel() {

    val trainingList: LiveData<List<Training>> =
            fetchTrainingUseCase.execute()
                    .subscribeOn(schedulersProvider.io())
                    .observeOn(schedulersProvider.ui())
                    .toLiveData()

    val snackBarStatus: LiveData<Boolean>
    private val _updateList: MutableLiveData<List<Training>> = MutableLiveData()

    init {
        snackBarStatus = Transformations.switchMap(_updateList) {
            updateSetting(it)
        }
    }

    fun initSetting() {
        useCase.initApp()
    }

    fun update(trainingList: List<Training>) {
        _updateList.postValue(trainingList)
    }

    private fun updateSetting(trainingList: List<Training>) =
            useCase.updateTraining(trainingList).toLiveData()
}