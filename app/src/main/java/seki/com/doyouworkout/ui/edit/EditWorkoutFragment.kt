package seki.com.doyouworkout.ui.edit

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit_workout.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.OneDayWorkout
import java.text.SimpleDateFormat
import java.util.*

class EditWorkoutFragment: Fragment() {

    private lateinit var viewModel: EditWorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_workout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = (activity as EditWorkoutActivity).viewModel
        viewModel.showData.observe(this, Observer { showWorkout(it) })
    }

    private fun showWorkout(workout: OneDayWorkout?) {
        workout ?: return

        date.text =
                SimpleDateFormat("yyyy/MM/DD", Locale.US)
                        .format(workout.trainingDate)
    }

}