package seki.com.doyouworkout.ui.mainlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.edit.EditWorkoutActivity
import seki.com.doyouworkout.ui.setting.SettingActivity

class MainListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { showEditWorkoutScreen() }
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

    private fun showSettingScreen() {
        val intent = SettingActivity.getInstant(this)
        startActivity(intent)
    }

    private fun showEditWorkoutScreen() {
        val intent = EditWorkoutActivity.getIntent(this)
        startActivity(intent)
    }
}
