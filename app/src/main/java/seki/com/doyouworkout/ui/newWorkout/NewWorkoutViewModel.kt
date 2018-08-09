package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.Workout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.WorkoutUseCase
import java.util.*
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(private val useCase: WorkoutUseCase): ViewModel() {

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
            if (date != null) {
                useCase.getWorkout(date).toLiveData()
            } else {
                useCase.fetchEmptyWorkout().toLiveData()
            }
        }
    }

    fun update(trainingList: List<Workout>) {
        _workoutList.postValue(OneDayWorkout(_trainingDate.value?: Date(), trainingList))
    }

    private fun updateWorkout(workoutList: List<Workout>, data: Date): LiveData<Boolean> {
        return useCase.updateWorkout(data, workoutList).toLiveData()
    }

    fun showWorkoutAt(date: Date?) {
        _trainingDate.postValue(date)
    }
}