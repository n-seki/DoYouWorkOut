package seki.com.doyouworkout.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.mainlist.MainListActivity
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity
import seki.com.doyouworkout.ui.setting.SettingActivity

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainListActivity(): MainListActivity

    @ContributesAndroidInjector
    fun contributeNewWorkoutActivity(): NewWorkoutActivity

    @ContributesAndroidInjector
    fun contributeSettingActivity(): SettingActivity
}