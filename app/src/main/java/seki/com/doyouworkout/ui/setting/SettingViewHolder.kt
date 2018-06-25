package seki.com.doyouworkout.ui.setting

import android.support.v7.widget.RecyclerView
import android.view.View
import seki.com.doyouworkout.databinding.LayoutTrainingListItemBinding

class SettingViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val bind: LayoutTrainingListItemBinding by lazy {
        LayoutTrainingListItemBinding.bind(view) }
}