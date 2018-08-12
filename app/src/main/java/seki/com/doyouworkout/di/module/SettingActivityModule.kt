package seki.com.doyouworkout.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.setting.SettingFragment

@Module interface SettingActivityModule {
    @ContributesAndroidInjector
    fun contributeNewWorkoutFragment(): SettingFragment
}