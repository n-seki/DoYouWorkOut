package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_new_workout.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.PutTrainingCountDialog
import seki.com.doyouworkout.ui.Workout

class NewWorkoutFragment: Fragment(), PutTrainingCountDialog.OnCompleteInputListener {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_new_workout, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = (activity as NewWorkoutActivity).viewModel
        viewModel.trainingList.observe(this, Observer { showList(it) })
    }

    private fun showList(list: List<Workout>?) {
        list?.let {
            val listener = object : NewWorkoutListAdapter.WorkoutClickListener {
                override fun onClickWorkoutCount(workout: Workout) {
                    showPutCountDialog(workout)
                }
            }
            val adapter = NewWorkoutListAdapter(it, listener)
            training_list.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            training_list.addItemDecoration(divider)
            training_list.adapter = adapter
        }
    }

    private fun showPutCountDialog(workout: Workout) {
        PutTrainingCountDialog
                .newInstance(workout.id, workout.name, workout.count)
                .show(childFragmentManager, "put_count")
    }

    override fun onCompleteInputCount(id: Int, count: Int) {
        val adapter = training_list.adapter as NewWorkoutListAdapter
        val (position, item) = adapter.getItemWithPositionByWorkoutId(id)
        item.count = count
        adapter.notifyItemChanged(position, item)
    }
}