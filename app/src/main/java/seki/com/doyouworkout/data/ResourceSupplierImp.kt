package seki.com.doyouworkout.data

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class ResourceSupplierImp @Inject constructor(private val context: Context): ResourceSupplier {
    override fun getString(@StringRes id: Int) = context.getString(id) ?: ""
}