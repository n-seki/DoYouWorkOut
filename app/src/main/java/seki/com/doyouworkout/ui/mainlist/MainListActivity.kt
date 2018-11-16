package seki.com.doyouworkout.ui.mainlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity
import seki.com.doyouworkout.ui.setting.SettingActivity
import javax.inject.Inject

class MainListActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: MainListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { showEditWorkoutScreen() }

        AndroidInjection.inject(this)

        viewModel.checkInitApp { showSettingScreenIfNotInit(it) }
        viewModel.hasTodayWorkout.observe(this, Observer { changeFabVisibility(it) })
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

    private fun showSettingScreenIfNotInit(settingComplete: Boolean) {
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

    private fun changeFabVisibility(hasTodayWorkout: Boolean?) {
        hasTodayWorkout ?: return
        fab.isVisible = !hasTodayWorkout
    }

}
