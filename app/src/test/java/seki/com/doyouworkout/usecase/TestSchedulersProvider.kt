package seki.com.doyouworkout.usecase

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object TestSchedulersProvider: SchedulersProviderBase {
    override fun ui(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
}