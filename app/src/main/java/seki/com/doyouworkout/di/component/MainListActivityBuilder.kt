package seki.com.doyouworkout.di.component

import dagger.Module
import dagger.android.ContributesAndroidInjector
import seki.com.doyouworkout.ui.mainlist.MainListActivity

@Module
interface MainListActivityBuilder {

    @ContributesAndroidInjector
    fun contributeMainListActivity(): MainListActivity
}