package seki.com.doyouworkout.di.component

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.setting.SettingActivity

@Module
interface SettingActivityBuilder {

    @ContributesAndroidInjector
    fun contributeMainListActivity(): SettingActivity
}