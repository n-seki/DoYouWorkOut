package seki.com.doyouworkout.di.component

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.di.module.MainListActivityModule
import seki.com.doyouworkout.ui.mainlist.MainListActivity

@Module interface MainListActivityBuilder {
    @Binds fun proviedsAppCompatActivity(activity: MainListActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [MainListActivityModule::class])
    fun contributeMainListActivity(): MainListActivity
}