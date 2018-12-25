package seki.com.doyouworkout

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import seki.com.doyouworkout.di.ApplicationComponent
import seki.com.doyouworkout.di.ApplicationModule
import seki.com.doyouworkout.di.DaggerApplicationComponent

class App: dagger.android.DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    lateinit var appComponent: ApplicationComponent
}