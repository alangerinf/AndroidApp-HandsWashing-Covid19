package com.alangerdev.handswasher.wash_steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_pre.*
import com.alangerdev.handswasher.MainActivity
import com.alangerdev.handswasher.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PreStepsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStart.setOnClickListener {
            btnStart.isEnabled=false
            val activity = activity as MainActivity
            activity.startWash()

        }

    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        activity.setPos(0)
    }
}
