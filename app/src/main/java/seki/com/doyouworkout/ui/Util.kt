package seki.com.doyouworkout.ui

import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Single
import org.reactivestreams.Publisher

fun <T> Publisher<T>.toLiveData() =
        LiveDataReactiveStreams.fromPublisher(this)

fun <T> Single<T>.toLiveData() =
        toFlowable().toLiveData()