package seki.com.doyouworkout

import android.app.Application
import seki.com.doyouworkout.di.ApplicationComponent
import seki.com.doyouworkout.di.ApplicationModule
import seki.com.doyouworkout.di.DaggerApplicationComponent

class App: Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}