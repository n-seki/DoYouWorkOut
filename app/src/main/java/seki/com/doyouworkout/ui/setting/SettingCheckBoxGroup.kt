package seki.com.doyouworkout.ui.setting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_custom_switch.view.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Training

class SettingCheckBoxGroup @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var data: MutableMap<Int, Training>

    fun init(
            trainingList: List<Training>,
            listener: View.OnClickListener
    ) {
        clear()
        data = trainingList
                .associateBy { it.id }
                .toMutableMap()

        for ((_, training) in data) {
            addView(createSwitch(training, listener))
        }
    }

    private fun createSwitch(
            training: Training,
            listener: View.OnClickListener
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.view_custom_switch, this, false).apply {
            switchView.apply {
                text = training.name
                isChecked = training.isUsed
                tag = training.id

                setOnCheckedChangeListener { switch, isChecked ->
                    val id = switch.tag as Int
                    updateData(id, isChecked)
                    listener.onClick(switch)
                }
            }
        }
    }

    private fun updateData(id: Int, isUsed: Boolean) {
        data[id] = data[id]?.copy(isUsed = isUsed)!!
    }

    fun fetchCheckedData(): List<Training> {
        return data.values.toList()
    }

    private fun clear() {
        removeAllViews()

        if (::data.isInitialized) {
            data.clear()
        }
    }

}