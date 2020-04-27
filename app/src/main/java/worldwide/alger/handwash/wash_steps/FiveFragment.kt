package worldwide.alger.handwash.wash_steps

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_four.*
import worldwide.alger.handwash.MainActivity
import worldwide.alger.handwash.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FiveFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        startAnimation()
    }
    val animator = ValueAnimator.ofFloat(0f, 0.1f).setDuration(MainActivity.duracion.get(4).toLong())
    @SuppressLint("WrongConstant")
    private fun startAnimation() {
        animator.addUpdateListener { valueAnimator ->
            lottie.progress = valueAnimator.animatedValue as Float
        }
        animator.start()
    }
    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        activity.setPos(5)
    }
    override fun onPause() {
        super.onPause()
        animator.pause()
    }
}
