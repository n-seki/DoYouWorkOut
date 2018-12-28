package seki.com.doyouworkout.ui.setting

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Switch
import seki.com.doyouworkout.ui.Training

class SettingCheckBoxGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var data: MutableMap<Int, Training>

    fun init(trainingList: List<Training>) {
        clear()
        data = trainingList
                .associateBy { it.id }
                .toMutableMap()

        for ((_, training) in data) {
            addView(createSwitch(training))
        }
    }

    private fun createSwitch(training: Training): Switch {
        return Switch(context).apply {
            text = training.name
            isChecked = training.isUsed
            textSize = 20F
            setPadding(8, 16, 8, 8)
            tag = training.id
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        listener?.let {
            for (position in 0..childCount) {
                val view = getChildAt(position) as? Switch ?: continue
                view.setOnCheckedChangeListener { switch, isChecked ->
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