package seki.com.doyouworkout.di

import dagger.Component
import seki.com.doyouworkout.App

@Component(modules = [ApplicationComponent::class])
interface ApplicationComponent {
    fun inject(application: App)
}