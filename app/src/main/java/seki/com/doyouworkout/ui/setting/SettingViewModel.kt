package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.FetchTrainingUseCase
import seki.com.doyouworkout.usecase.SchedulersProviderBase
import seki.com.doyouworkout.usecase.TrainingUseCase
import seki.com.doyouworkout.usecase.UpdateTrainingUseCase
import javax.inject.Inject

class SettingViewModel @Inject constructor(
        private val useCase: TrainingUseCase,
        fetchTrainingUseCase: FetchTrainingUseCase,
        private val updateTrainingUseCase: UpdateTrainingUseCase,
        private val schedulersProvider: SchedulersProviderBase
): ViewModel() {

    val trainingList: LiveData<List<Training>> =
            fetchTrainingUseCase.execute()
                    .subscribeOn(schedulersProvider.io())
                    .observeOn(schedulersProvider.ui())
                    .toLiveData()

    fun initSetting() {
        useCase.initApp()
    }

    fun update(trainingList: List<Training>) {
        updateTrainingUseCase.execute(trainingList)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe()
    }
}