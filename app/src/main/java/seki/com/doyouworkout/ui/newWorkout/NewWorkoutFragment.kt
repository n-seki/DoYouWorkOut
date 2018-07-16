package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_new_workout.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Workout

class NewWorkoutFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_new_workout, container!!, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = (activity as NewWorkoutActivity).viewModel
        viewModel.trainingList.observe(this, Observer { showList(it) })
    }

    private fun showList(list: List<Workout>?) {
        list?.let {
            val adapter = NewWorkoutListAdapter(it)
            training_list.layoutManager = LinearLayoutManager(
                    context,LinearLayoutManager.INVALID_OFFSET, false)
            training_list.adapter = adapter
        }
    }
}