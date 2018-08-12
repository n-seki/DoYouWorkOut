package seki.com.doyouworkout.ui.setting

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import javax.inject.Inject

class SettingActivity: DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: SettingViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java) }

    private val isCompleteSetting by lazy {
        intent.getBooleanExtra(SETTING_COMPLETE, false)
    }

    companion object {
        const val SETTING_COMPLETE = "setting_complete"
        fun getInstance(context: Context, settingComplete: Boolean) =
                Intent(context, SettingActivity::class.java).apply {
                    putExtra(SETTING_COMPLETE, settingComplete)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)

        if (!isCompleteSetting) {
            initSetting()
        }

    }

    private fun initSetting() {
        viewModel.initSetting()
        Toast.makeText(this, "Please First Setting!", Toast.LENGTH_LONG).show()
    }
}