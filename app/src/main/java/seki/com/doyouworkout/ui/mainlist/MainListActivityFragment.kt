package seki.com.doyouworkout.ui.mainlist

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import seki.com.doyouworkout.R

/**
 * A placeholder fragment containing a simple view.
 */
class MainListActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }
}
