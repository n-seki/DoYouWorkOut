package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_main_list.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.OneDayWorkout
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity

/**
 * A placeholder fragment containing a simple view.
 */
class MainListFragment : Fragment() {

    private val listener = object : MainWorkoutListAdapter.OnClickListener {
        override fun onClickItem(oneDayWorkout: OneDayWorkout) {
            showEditWorkoutScreen(oneDayWorkout)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = (activity as MainListActivity).viewModel
        viewModel.workoutList.observe(this, Observer { dataList ->
            dataList?.let {
                val adapter = workout_list.adapter as MainWorkoutListAdapter
                adapter.data = it
                if (list_empty_text.isVisible) {
                    list_empty_text.visibility = View.GONE
                }
            }
        })
    }

    private fun initView(view: View) {
        val list = view.findViewById<RecyclerView>(R.id.workout_list)
        list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decorator)
        list.adapter = MainWorkoutListAdapter(listener)
    }

    private fun showEditWorkoutScreen(oneDayWorkout: OneDayWorkout) {
        val intent = NewWorkoutActivity.getIntent(context!!, oneDayWorkout.trainingDate)
        activity?.run {
            startActivity(intent)
        }
    }
}
