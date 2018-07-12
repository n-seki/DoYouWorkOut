package seki.com.doyouworkout.ui.edit

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.NumberPicker
import seki.com.doyouworkout.R
import seki.com.doyouworkout.databinding.LayoutEditWorkoutItemBinding

class EditWorkoutViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binder: LayoutEditWorkoutItemBinding by lazy {
        view.findViewById<NumberPicker>(R.id.training_count_picker).apply {
            maxValue = 99
            minValue = 0
        }

        LayoutEditWorkoutItemBinding.bind(view)
    }
}