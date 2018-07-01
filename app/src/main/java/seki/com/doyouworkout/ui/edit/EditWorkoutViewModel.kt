package seki.com.doyouworkout.ui.edit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.toLiveData
import seki.com.doyouworkout.usecase.WorkoutUseCase
import java.util.*
import javax.inject.Inject

class EditWorkoutViewModel
@Inject constructor(private val useCase: WorkoutUseCase): ViewModel() {

    private val _date: MutableLiveData<Date> = MutableLiveData()
    val showData: LiveData<OneDayWorkout>

    init {
        showData = Transformations.switchMap(_date) {
            useCase.getWorkout(it).toLiveData()
        }
    }

    fun showWorkoutAt(date: Date) {
        _date.postValue(date)
    }
}