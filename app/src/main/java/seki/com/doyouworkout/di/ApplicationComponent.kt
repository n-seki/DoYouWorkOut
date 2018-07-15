package seki.com.doyouworkout.di

import dagger.Component
import seki.com.doyouworkout.App
import seki.com.doyouworkout.ui.edit.EditWorkoutActivity
import seki.com.doyouworkout.ui.mainlist.MainListActivity
import seki.com.doyouworkout.ui.newWorkout.NewWorkoutActivity
import seki.com.doyouworkout.ui.setting.SettingActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, WorkoutViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: App)
    fun inject(settingActivity: SettingActivity)
    fun inject(mainListActivity: MainListActivity)
    fun inject(editWorkoutActivity: EditWorkoutActivity)
    fun inject(newWorkoutActivity: NewWorkoutActivity)
}