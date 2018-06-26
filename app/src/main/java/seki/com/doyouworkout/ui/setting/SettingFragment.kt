package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_setting.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.Training

class SettingFragment: Fragment() {

    lateinit var viewModel: SettingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        regist_setting.setOnClickListener({
            val stateChangeTrainingList = training_check_boxes.fetchCheckedData()
            viewModel.registSetting(stateChangeTrainingList)
        })

        viewModel = (context as SettingActivity).viewModel
        viewModel.loadTraining().observe(this, Observer { showTrainingList(it) })
    }

    private fun showTrainingList(trainingList: List<Training>?) {
        trainingList?.let {
            training_check_boxes.init(it)
        }
    }
}