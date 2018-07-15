package seki.com.doyouworkout.ui.edit

import android.support.v7.widget.RecyclerView
import android.view.View
import seki.com.doyouworkout.databinding.LayoutEditWorkoutItemBinding

class EditWorkoutViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binder: LayoutEditWorkoutItemBinding by lazy {
        LayoutEditWorkoutItemBinding.bind(view)
    }
}