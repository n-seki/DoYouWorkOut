package seki.com.doyouworkout.data

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class ResourceSupplier @Inject constructor(private val context: Context) {

    fun getString(@StringRes id: Int) = context.getString(id) ?: ""
}