package seki.com.doyouworkout.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import seki.com.doyouworkout.App
import seki.com.doyouworkout.usecase.UseCaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    UseCaseModule::class,
    WorkoutViewModelModule::class
])
interface ApplicationComponent: AndroidInjector<App>