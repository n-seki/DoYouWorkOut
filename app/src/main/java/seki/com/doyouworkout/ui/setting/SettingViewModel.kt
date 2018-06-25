package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.ui.Training
import seki.com.doyouworkout.ui.Workout
import javax.inject.Inject

class SettingViewModel @Inject constructor(private val repo: WorkoutRepository): ViewModel() {

    fun loadTraining(): LiveData<List<Training>> {
        val liveData = MutableLiveData<List<Training>>()
        liveData.postValue(repo.getDummyTraining())
        return liveData
    }
}