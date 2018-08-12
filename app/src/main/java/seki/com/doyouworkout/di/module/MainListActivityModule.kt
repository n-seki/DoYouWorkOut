package seki.com.doyouworkout.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.mainlist.MainListFragment

@Module interface MainListActivityModule {
    @ContributesAndroidInjector
    fun contributeMainListFragment(): MainListFragment
}