package seki.com.doyouworkout.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.dialog_put_training_count.view.*
import seki.com.doyouworkout.R

class PutTrainingCountDialog: DialogFragment() {

    private lateinit var listener: OnCompleteInputListener

    companion object {
        const val WORKOUT_ID = "workout_id"
        const val WORKOUT_NAME = "workout_name"
        const val WORKOUT_COUNT = "workout_count"
        fun newInstance(id: Int, name: String, currentCount: Int): DialogFragment {
            return PutTrainingCountDialog().apply {
                arguments = Bundle().apply {
                    putInt(WORKOUT_ID, id)
                    putString(WORKOUT_NAME, name)
                    putInt(WORKOUT_COUNT, currentCount)
                }
            }
        }
    }

    interface OnCompleteInputListener {
        fun onCompleteInputCount(id: Int, count: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val layout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_put_training_count, null)

        initView(layout)

        return AlertDialog.Builder(context!!)
                .setView(layout)
                .create().apply {
                    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = parentFragment as OnCompleteInputListener
    }

    private fun initView(layout: View) {
        val workoutId = arguments?.getInt(WORKOUT_ID) ?: throw IllegalStateException()
        val workoutName = arguments?.getString(WORKOUT_NAME) ?: throw IllegalStateException()
        val workoutCount = arguments?.getInt(WORKOUT_COUNT) ?: throw IllegalStateException()

        layout.title.text = workoutName

        layout.training_count_picker.run {
            maxValue = 99
            minValue = 0
            value = workoutCount
        }

        layout.commit_button.setOnClickListener {
            listener.onCompleteInputCount(workoutId, layout.training_count_picker.value)
            dismiss()
        }

        layout.cancel_button.setOnClickListener {
            dismiss()
        }
    }
}