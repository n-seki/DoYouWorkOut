package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.*
import seki.com.doyouworkout.domain.TrainingDomain
import seki.com.doyouworkout.ui.Training
import javax.inject.Inject

class SettingViewModel @Inject constructor(private val domain: TrainingDomain): ViewModel() {

    fun loadTraining(): LiveData<List<Training>> {
        val liveData = MutableLiveData<List<Training>>()
        liveData.postValue(domain.getAllTraining())
        return liveData
    }

    fun registSetting(trainingList: List<Training>) {
        domain.updateTraining(trainingList)
    }
}