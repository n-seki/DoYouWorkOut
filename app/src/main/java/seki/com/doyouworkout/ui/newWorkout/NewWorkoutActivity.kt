package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import seki.com.doyouworkout.App
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import javax.inject.Inject

class NewWorkoutActivity: AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) =
                Intent(context, NewWorkoutActivity::class.java)
    }

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: NewWorkoutViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewWorkoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)

        (application as App).appComponent.inject(this)
    }
}