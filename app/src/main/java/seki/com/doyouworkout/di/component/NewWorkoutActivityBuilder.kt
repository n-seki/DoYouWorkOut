package seki.com.doyouworkout.di.component

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity

@Module
interface NewWorkoutActivityBuilder {

    @ContributesAndroidInjector
    fun contributeMainListActivity(): NewWorkoutActivity
}