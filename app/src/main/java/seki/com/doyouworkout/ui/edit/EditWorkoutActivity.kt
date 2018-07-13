package seki.com.doyouworkout.ui.edit

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.App
import seki.com.doyouworkout.R
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import java.util.*
import javax.inject.Inject

class EditWorkoutActivity: AppCompatActivity() {

    @Inject lateinit var viewModelFactory: WorkoutViewModelFactory
    val viewModel: EditWorkoutViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
                .get(EditWorkoutViewModel::class.java)
    }

    companion object {
        private const val DATE = "date"

        fun getIntent(context: Context, date: Date? = null) =
                Intent(context, EditWorkoutActivity::class.java).apply {
                    date.let { putExtra(DATE, it) }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workout)
        setSupportActionBar(toolbar)

        (application as App).appComponent.inject(this)

        val date = intent.getSerializableExtra(DATE) as? Date
        date?.let {
            viewModel.showWorkoutAt(it)
        }
    }

}