package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_new_workout.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.PutTrainingCountDialog
import seki.com.doyouworkout.ui.Workout

class NewWorkoutFragment: Fragment(), PutTrainingCountDialog.OnCompleteInputListener {

    lateinit var viewModel: NewWorkoutViewModel
    lateinit var listener: FragmentClickListener

    interface FragmentClickListener {
        fun onClickCancel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_new_workout, null, false)
        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            listener.onClickCancel()
        }
        view.findViewById<Button>(R.id.commit_button).setOnClickListener {
            updateWorkout()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = (activity as NewWorkoutActivity).viewModel
        viewModel.trainingList.observe(this, Observer { showList(it) })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = (context as FragmentClickListener)
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
            training_list.adapter = adapter
        }
    }

    private fun updateWorkout() {
        val adapter = training_list.adapter as NewWorkoutListAdapter
        viewModel.update(adapter.trainingList)
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