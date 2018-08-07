package seki.com.doyouworkout.ui.mainlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.OneDayWorkout

class MainWorkoutListAdapter(val listener: MainWorkoutListAdapter.OnClickListener): RecyclerView.Adapter<MainWorkoutViewHolder>() {

    interface OnClickListener {
        fun onClickItem(oneDayWorkout: OneDayWorkout)
    }

    var data = listOf<OneDayWorkout>()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_workout_list_item, parent, false)
        return MainWorkoutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainWorkoutViewHolder, position: Int) {
        holder.binder.oneDayWorkout = data[position]
        holder.binder.root.setOnClickListener {
            listener.onClickItem(data[position])
        }
    }
}