package seki.com.doyouworkout.ui.newWorkout

import android.support.v7.widget.RecyclerView
import android.view.View
import seki.com.doyouworkout.databinding.LayoutNewWorkoutItemBinding

class NewWorkoutViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binder: LayoutNewWorkoutItemBinding by lazy {
        LayoutNewWorkoutItemBinding.bind(view)
    }
}