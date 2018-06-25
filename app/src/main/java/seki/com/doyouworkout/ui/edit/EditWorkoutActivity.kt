package seki.com.doyouworkout.ui.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*
import seki.com.doyouworkout.R

class EditWorkoutActivity: AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, EditWorkoutActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workout)
        setSupportActionBar(toolbar)
    }

}