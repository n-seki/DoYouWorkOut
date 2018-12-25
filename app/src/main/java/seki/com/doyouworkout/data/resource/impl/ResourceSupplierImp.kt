package seki.com.doyouworkout.data.resource.impl

import android.content.Context
import android.support.annotation.StringRes
import seki.com.doyouworkout.data.resource.ResourceSupplier
import javax.inject.Inject

class ResourceSupplierImp @Inject constructor(
        private val context: Context
) : ResourceSupplier {
    override fun getString(@StringRes id: Int): String {
        return context.getString(id) ?: ""
    }
}