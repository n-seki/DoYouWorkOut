package seki.com.doyouworkout.ui.newWorkout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import java.util.*
import javax.inject.Inject

class NewWorkoutActivity: DaggerAppCompatActivity(), NewWorkoutFragment.FragmentClickListener {

    companion object {
        private const val DATE = "date"

        fun getIntent(context: Context, date: Date? = null) =
                Intent(context, NewWorkoutActivity::class.java).apply {
                    putExtra(DATE, date)
                }
    }

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: NewWorkoutViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewWorkoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.updateStatus.observe(this, Observer { onUpdateFinish(it) })

        val trainingDate = intent.getSerializableExtra(DATE) as? Date
        viewModel.showWorkoutAt(trainingDate)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item ?: return super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
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