package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.GetWorkoutUseCase
import seki.com.doyouworkout.usecase.SchedulersProviderBase
import seki.com.doyouworkout.usecase.UpdateWorkoutUseCase
import java.util.*
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(
        private val schedulerProvider: SchedulersProviderBase,
        private val getWorkoutUseCase: GetWorkoutUseCase,
        private val updateWorkoutUseCase: UpdateWorkoutUseCase
): ViewModel() {

    val trainingList: LiveData<List<Workout>>

    private val _trainingDate: MutableLiveData<Date> = MutableLiveData()
    val date: LiveData<Date> = _trainingDate

    private val _workoutList: MutableLiveData<OneDayWorkout> = MutableLiveData()
    val updateStatus: LiveData<Boolean>

    init {
        updateStatus = Transformations.switchMap(_workoutList) {
            updateWorkout(it.workout, it.trainingDate)
        }

        trainingList = Transformations.switchMap(_trainingDate) { date ->
                getWorkoutUseCase.execute(date)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toLiveData()
        }
    }

    fun update(trainingList: List<Workout>) {
        _workoutList.postValue(OneDayWorkout(_trainingDate.value?: Date(), trainingList))
    }

    private fun updateWorkout(workoutList: List<Workout>, data: Date): LiveData<Boolean> {
        return updateWorkoutUseCase.execute(data, workoutList)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .toLiveData()
    }

    fun showWorkoutAt(date: Date?) {
        _trainingDate.postValue(date)
    }
}