package com.alangerdev.handswasher.wash_steps

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_four.lottie
import com.alangerdev.handswasher.MainActivity
import com.alangerdev.handswasher.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThreeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
    }
    val animator = ValueAnimator.ofFloat(0.5f, 0.6f).setDuration(MainActivity.duracion.get(2).toLong())
    @SuppressLint("WrongConstant")
    private fun startAnimation() {
        animator.addUpdateListener { valueAnimator ->
            lottie.progress = valueAnimator.animatedValue as Float
        }
        animator.start()
    }
    override fun onResume() {
        super.onResume()

        startAnimation()
        val activity = activity as MainActivity
        activity.setPos(3)

    }
    override fun onPause() {
        super.onPause()
        animator.pause()
    }
}
