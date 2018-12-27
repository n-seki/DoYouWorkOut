package seki.com.doyouworkout.data.resource.impl

import seki.com.doyouworkout.data.resource.DateSupplier
import java.util.*

class DateSupplierImp : DateSupplier {
    override fun getToday(): Date {
        return Date()
    }
}