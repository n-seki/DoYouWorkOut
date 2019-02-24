package seki.com.doyouworkout

import android.annotation.SuppressLint
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import seki.com.doyouworkout.di.ApplicationModule
import seki.com.doyouworkout.di.DaggerApplicationComponent

@SuppressLint("Registered")
open class BaseApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}