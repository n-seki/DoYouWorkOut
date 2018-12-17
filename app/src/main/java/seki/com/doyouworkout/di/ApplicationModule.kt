package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.DateSupplier
import seki.com.doyouworkout.data.DateSupplierImp
import seki.com.doyouworkout.data.ResourceSupplier
import seki.com.doyouworkout.data.ResourceSupplierImp
import seki.com.doyouworkout.data.cache.Cache
import seki.com.doyouworkout.data.cache.DataCache
import seki.com.doyouworkout.data.db.AppDataBase
import seki.com.doyouworkout.data.repository.LocalRepository
import seki.com.doyouworkout.data.repository.LocalRepositoryImp
import seki.com.doyouworkout.data.repository.Repository
import seki.com.doyouworkout.data.repository.WorkoutRepository
import seki.com.doyouworkout.usecase.SchedulersProvider
import seki.com.doyouworkout.usecase.SchedulersProviderBase
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideDataBase(): AppDataBase {
        return Room.databaseBuilder(applicationContext, AppDataBase::class.java, "workout").build()
    }

    @Singleton
    @Provides
    fun provideCache(): Cache {
        return DataCache()
    }

    @Singleton
    @Provides
    fun provideRepository(localRepository: LocalRepository, cache: Cache): Repository {
        return WorkoutRepository(localRepository, cache)
    }

    @Singleton
    @Provides
    fun provideLocalRepository(
            db: AppDataBase,
            sharedPreferences: SharedPreferences,
            resourceSupplier: ResourceSupplier
    ): LocalRepository {
        return LocalRepositoryImp(db, sharedPreferences, resourceSupplier)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences =
            applicationContext.getSharedPreferences("workout", MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideResourceSupplier(): ResourceSupplier =
            ResourceSupplierImp(applicationContext)

    @Singleton
    @Provides
    fun provideDateSupplier(): DateSupplier {
        return DateSupplierImp()
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulersProviderBase {
        return SchedulersProvider()
    }
}