package seki.com.doyouworkout.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.dialog_put_training_count.view.*
import seki.com.doyouworkout.R

class PutTrainingCountDialog: DialogFragment() {

    private lateinit var listener: OnCompleteInputListener
    private lateinit var workout: Workout

    companion object {
        const val WORKOUT = "workout"
        fun newInstance(workout: Workout): DialogFragment {
            return PutTrainingCountDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(WORKOUT, workout)
                }
            }
        }
    }

    interface OnCompleteInputListener {
        fun onCompleteInputCount()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val layout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_put_training_count, null)

        initView(layout)

        builder.setView(view)
        return builder.create()
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)

        if (childFragment is OnCompleteInputListener) {
            listener = childFragment
        }
        workout = arguments?.getSerializable(WORKOUT) as Workout
    }

    private fun initView(layout: View) {
        layout.title.text = workout.name

        layout.training_count_picker.run {
            maxValue = 99
            minValue = 0
        }

        layout.commit_button.setOnClickListener {
            listener.onCompleteInputCount()
        }
    }
}