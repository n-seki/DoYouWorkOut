package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_list.*
import seki.com.doyouworkout.R
import kotlinx.android.synthetic.main.fragment_setting.*
import seki.com.doyouworkout.ui.Training

class SettingFragment: Fragment() {

    lateinit var viewModel: SettingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        viewModel = (context as SettingActivity).viewModel
        viewModel.loadTraining().observe(this, Observer { showTrainingList(it) })
    }

    private fun initView() {
        training_list.adapter = SettingListAdapter()
        training_list.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showTrainingList(trainingList: List<Training>?) {
        trainingList?.let {
            (training_list.adapter as SettingListAdapter).data = it
        }
    }
}