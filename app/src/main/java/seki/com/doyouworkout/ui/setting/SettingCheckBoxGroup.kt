package seki.com.doyouworkout.ui.setting

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.LinearLayout
import seki.com.doyouworkout.ui.Training

class SettingCheckBoxGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var data: Map<Int, Training>

    fun init(trainingList: List<Training>) {
        clear()
        data = trainingList.associateBy { it.id }

        for ((_, training) in data) {
            addView(createCheckBox(training))
        }
    }

    private fun createCheckBox(training: Training): CheckBox {
        return CheckBox(context).apply {
            text = training.name
            isChecked = training.isUsed
            textSize = 30F
            setPadding(8,16,8,8)
            tag = training.id
        }
    }

    fun fetchCheckedData(): List<Training> {
        val checkStatus = mutableMapOf<Int, Boolean>()
        for (position in 0..childCount) {
            val view = getChildAt(position) as? CheckBox ?: continue
            checkStatus += (view.tag as Int) to (view.isChecked)
        }

        val trainingList = mutableListOf<Training>()
        for ((id, status) in checkStatus) {
            val training = data[id] ?: continue
            trainingList += training.copy(isUsed = status)
        }

        return trainingList
    }

    private fun clear() {
        removeAllViews()
    }

}