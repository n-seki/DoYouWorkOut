package seki.com.doyouworkout.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.dialog_put_training_count.view.*
import seki.com.doyouworkout.R

class PutTrainingCountDialog: DialogFragment() {

    private lateinit var listener: OnCompleteInputListener

    companion object {
        const val WORKOUT_ID = "workout_id"
        const val WORKOUT_NAME = "workout_name"
        fun newInstance(id: Int, name: String): DialogFragment {
            return PutTrainingCountDialog().apply {
                arguments = Bundle().apply {
                    putInt(WORKOUT_ID, id)
                    putString(WORKOUT_NAME, name)
                }
            }
        }
    }

    interface OnCompleteInputListener {
        fun onCompleteInputCount(id: Int, count: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val layout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_put_training_count, null)

        initView(layout)

        builder.setView(layout)
        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = parentFragment as OnCompleteInputListener
    }

    private fun initView(layout: View) {
        val workoutId = arguments?.getInt(WORKOUT_ID) ?: throw IllegalStateException()
        val workoutName = arguments?.getString(WORKOUT_NAME) ?: throw IllegalStateException()

        layout.title.text = workoutName

        layout.training_count_picker.run {
            maxValue = 99
            minValue = 0
        }

        layout.commit_button.setOnClickListener {
            listener.onCompleteInputCount(workoutId, layout.training_count_picker.value)
            dismiss()
        }
    }
}