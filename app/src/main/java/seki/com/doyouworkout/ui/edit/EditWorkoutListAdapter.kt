package seki.com.doyouworkout.ui.edit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Workout

class EditWorkoutListAdapter(
        val data: List<Workout>, private val listener: EditWorkoutListAdapter.TrainingClickListener) : RecyclerView.Adapter<EditWorkoutViewHolder>() {

    interface TrainingClickListener {
        fun onClickTrainingCount(workout: Workout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_edit_workout_item, parent, false)
        return EditWorkoutViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: EditWorkoutViewHolder, position: Int) {
        holder.binder.workout = data[position]
        holder.binder.trainingCount.setOnClickListener {
            listener.onClickTrainingCount(data[position])
        }
    }
}