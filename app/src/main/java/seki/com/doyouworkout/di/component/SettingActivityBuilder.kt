package seki.com.doyouworkout.di.component

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.di.module.SettingActivityModule
import seki.com.doyouworkout.ui.setting.SettingActivity

@Module interface SettingActivityBuilder {
    @Binds fun proviedsAppCompatActivity(activity: SettingActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [SettingActivityModule::class])
    fun contributeMainListActivity(): SettingActivity
}