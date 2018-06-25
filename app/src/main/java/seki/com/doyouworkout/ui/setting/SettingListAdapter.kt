package seki.com.doyouworkout.ui.setting

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Training

class SettingListAdapter: RecyclerView.Adapter<SettingViewHolder>() {

    var data: List<Training> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_training_list_item, null, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind.training = data[position]
    }

}