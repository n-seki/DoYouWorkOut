package seki.com.doyouworkout.usecase

import io.reactivex.Scheduler

interface SchedulersProviderBase {
    fun io(): Scheduler
    fun ui(): Scheduler
}