package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Training

class NewWorkoutListAdapter(private val trainingList: List<Training>): RecyclerView.Adapter<NewWorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_new_workout_item, parent, false)
        return NewWorkoutViewHolder(view)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: NewWorkoutViewHolder, position: Int) {
        holder.binder.training = trainingList[position]
    }

}