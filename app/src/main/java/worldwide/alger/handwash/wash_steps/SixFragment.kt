package worldwide.alger.handwash.wash_steps

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_four.lottie
import kotlinx.android.synthetic.main.fragment_six.*
import worldwide.alger.handwash.MainActivity
import worldwide.alger.handwash.R
import worldwide.alger.handwash.TTSManager

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SixFragment : Fragment() {
    val applauses : MediaPlayer by lazy{ MediaPlayer.create(activity,R.raw.applauses) }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_six, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ttsManager.init(activity)
    }
    val animator = ValueAnimator.ofFloat(0.9f, 1f).setDuration(MainActivity.duracion.get(5).toLong())
    @SuppressLint("WrongConstant")
    private fun startAnimation() {
        animator.addUpdateListener { valueAnimator ->
            lottie.progress = valueAnimator.animatedValue as Float
        }
        animator.start()
    }

    override fun onPause() {
        super.onPause()
        animator.pause()
    }
    val ttsManager: TTSManager = TTSManager()
    override fun onResume() {
        super.onResume()
        startAnimation()
        val activity = activity as MainActivity
        activity.setPos(6)
        btnEnd.visibility=View.GONE
        Handler().postDelayed({
            ttsManager.addQueue(getString(R.string.mensaje_fin))
            applauses.start()
            btnEnd.visibility=View.VISIBLE
            btnEnd.setOnClickListener {
               activity.setPos(0)
                findNavController().navigate(R.id.action_global_Pre)
                activity.showInterestial()
            }
        },MainActivity.duracion.get(5).toLong())
    }
}
