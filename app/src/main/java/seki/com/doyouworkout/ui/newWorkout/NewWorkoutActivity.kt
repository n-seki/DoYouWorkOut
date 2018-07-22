package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.App
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import javax.inject.Inject

class NewWorkoutActivity: AppCompatActivity(), NewWorkoutFragment.FragmentClickListener {

    companion object {
        fun getIntent(context: Context) =
                Intent(context, NewWorkoutActivity::class.java)
    }

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: NewWorkoutViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewWorkoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        (application as App).appComponent.inject(this)

        viewModel.updateStatus.observe(this, Observer { onUpdateFinish(it) })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item ?: return super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onUpdateFinish(updateStatus: Boolean?) {
        updateStatus?.let {
            if (it) {
                Toast.makeText(this, "Commit!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onClickCancel() {
        finish()
    }
}