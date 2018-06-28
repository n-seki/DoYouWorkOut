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

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initFab()

        viewModel = (context as SettingActivity).viewModel
        viewModel.trainingList.observe(this, Observer { showTrainingList(it) })
        viewModel.snackBarStatus.observe(this, Observer { showSnackBar(it) })
    }

    private fun initFab() {
        regist_setting.setOnClickListener({
            val stateChangeTrainingList = training_check_boxes.fetchCheckedData()
            viewModel.update(stateChangeTrainingList)
        })
    }

    private fun showTrainingList(trainingList: List<Training>?) {
        trainingList?.let {
            training_check_boxes.clear()
            training_check_boxes.init(it)
        }
    }

    private fun showSnackBar(boolean: Boolean?) {
        boolean?.let {
            if (it) {
                Snackbar.make(view!!, "Complete update!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}