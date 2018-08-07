package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.App
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity
import seki.com.doyouworkout.ui.setting.SettingActivity
import javax.inject.Inject

class MainListActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: MainListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { showEditWorkoutScreen() }

        (applicationContext as App).appComponent.inject(this)

        viewModel.initAppStatus.observe(this, Observer { checkSettingState(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showSettingScreen()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkSettingState(settingComplete: Boolean?) {
        settingComplete ?: return
        if (!settingComplete) {
            showSettingScreen(false)
        }
    }

    private fun showSettingScreen(settingComplete: Boolean = true) {
        val intent = SettingActivity.getInstance(this, settingComplete)
        startActivity(intent)
    }

    private fun showEditWorkoutScreen() {
        val intent = NewWorkoutActivity.getIntent(this)
        startActivity(intent)
    }
}
