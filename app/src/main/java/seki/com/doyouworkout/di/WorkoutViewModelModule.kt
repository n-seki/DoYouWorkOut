package seki.com.doyouworkout.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import seki.com.doyouworkout.ui.WorkoutViewModelFactory
import seki.com.doyouworkout.ui.edit.EditWorkoutViewModel
import seki.com.doyouworkout.ui.mainlist.MainListViewModel
import seki.com.doyouworkout.ui.setting.SettingViewModel

@Module
abstract class WorkoutViewModelModule {

    @Binds
    @IntoMap
    @WorkoutViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(viewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @WorkoutViewModelKey(MainListViewModel::class)
    abstract fun bindMainListViewModel(viewModel: MainListViewModel): ViewModel

    @Binds
    @IntoMap
    @WorkoutViewModelKey(EditWorkoutViewModel::class)
    abstract fun bindEditWorkoutViewModel(viewMode: EditWorkoutViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WorkoutViewModelFactory): ViewModelProvider.Factory
}