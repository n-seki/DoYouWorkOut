package seki.com.doyouworkout

import com.facebook.stetho.Stetho

class DebugApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}