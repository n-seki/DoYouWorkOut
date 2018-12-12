package seki.com.doyouworkout.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import seki.com.doyouworkout.App
import seki.com.doyouworkout.di.component.MainListActivityBuilder
import seki.com.doyouworkout.di.component.NewWorkoutActivityBuilder
import seki.com.doyouworkout.di.component.SettingActivityBuilder
import seki.com.doyouworkout.ui.setting.SettingActivity
import seki.com.doyouworkout.usecase.UseCaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    UseCaseModule::class,
    WorkoutViewModelModule::class,
    MainListActivityBuilder::class,
    NewWorkoutActivityBuilder::class,
    SettingActivityBuilder::class
])
interface ApplicationComponent: AndroidInjector<App> {
    fun inject(settingActivity: SettingActivity)
}