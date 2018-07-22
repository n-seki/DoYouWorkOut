package seki.com.doyouworkout.ui.newWorkout

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Workout

class NewWorkoutListAdapter(
        val trainingList: List<Workout>, private val listener: WorkoutClickListener): RecyclerView.Adapter<NewWorkoutViewHolder>() {

    interface WorkoutClickListener {
        fun onClickWorkoutCount(workout: Workout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_new_workout_item, parent, false)
        return NewWorkoutViewHolder(view)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: NewWorkoutViewHolder, position: Int) {
        holder.binder.workout = trainingList[position]
        holder.binder.root.setOnClickListener {
            listener.onClickWorkoutCount(trainingList[position])
        }
    }

    fun getItemWithPositionByWorkoutId(id: Int): Pair<Int, Workout> {
        return trainingList.withIndex().first { it.value.id == id }.let {
            it.index to it.value
        }
    }

}