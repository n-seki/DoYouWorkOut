package seki.com.doyouworkout.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import seki.com.doyouworkout.data.db.AppDataBase
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideDataBase() =
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "workout").build()
}