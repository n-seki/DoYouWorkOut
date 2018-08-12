package seki.com.doyouworkout.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutFragment

@Module interface NewWorkoutActivityModule {
    @ContributesAndroidInjector
    fun contributeNewWorkoutFragment(): NewWorkoutFragment
}