package seki.com.doyouworkout.data.resource

import android.support.annotation.StringRes

interface ResourceSupplier {
    fun getString(@StringRes id: Int): String
}