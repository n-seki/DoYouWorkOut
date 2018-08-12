package seki.com.doyouworkout.di.component

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.di.module.NewWorkoutActivityModule
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity

@Module interface NewWorkoutActivityBuilder {
    @Binds fun proviedsAppCompatActivity(activity: NewWorkoutActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [NewWorkoutActivityModule::class])
    fun contributeMainListActivity(): NewWorkoutActivity
}