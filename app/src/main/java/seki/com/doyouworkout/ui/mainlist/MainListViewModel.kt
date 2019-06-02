package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import seki.com.doyouworkout.data.equalsDay
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.GetOneDayWorkoutListUseCase
import seki.com.doyouworkout.usecase.SchedulersProviderBase
import seki.com.doyouworkout.usecase.TrainingUseCase
import java.util.*
import javax.inject.Inject

class MainListViewModel @Inject constructor(
        private val trainingUseCase: TrainingUseCase,
        private val getOneDayWorkoutListUseCase: GetOneDayWorkoutListUseCase,
        private val schedulersProvider: SchedulersProviderBase
): ViewModel() {

    private val disposables = CompositeDisposable()

    private val _workoutList: MediatorLiveData<List<OneDayWorkout>> = MediatorLiveData()
    val workoutList: LiveData<List<OneDayWorkout>> = _workoutList

    val hasTodayWorkout: LiveData<Boolean> = Transformations.switchMap(workoutList) { list ->
        containsTodayWorkout(list)
    }

    fun fetchList(startExclusive: Date? = null) {
        val list = getOneDayWorkoutListUseCase.execute(startExclusive)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .toLiveData()

        _workoutList.addSource(list) { _workoutList.postValue(it) }
    }

    fun checkInitApp(onSuccess: (Boolean) -> Unit) {
        onSuccess(trainingUseCase.isCompleteInitApp())
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}