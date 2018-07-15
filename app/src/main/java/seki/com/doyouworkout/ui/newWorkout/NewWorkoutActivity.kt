package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import javax.inject.Inject

class NewWorkoutActivity: AppCompatActivity() {

    @Inject private lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: NewWorkoutViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewWorkoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
    }
}