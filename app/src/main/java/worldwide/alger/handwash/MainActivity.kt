package worldwide.alger.handwash


import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_four.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val ttsManager: TTSManager = TTSManager()

    val beep : MediaPlayer  by lazy{ MediaPlayer.create(this@MainActivity,R.raw.beep) }
    private var model: MyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        mInterstitialAd = InterstitialAd(this)
        //mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.adUnitId = resources.getString(R.string.idInterestial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        ttsManager.init(this)
        model = ViewModelProvider(this).get(MyViewModel::class.java)

        model!!.counter.observe(this, Observer<String>{ time ->
            tViewTime.text = "${time}s"
        })
    }

    public fun setPos(pos :Int){
        tViewPos.text =  pos.toString()
        progressBar.progress=pos
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG,"onResume")
        destroy=false
        loader.visibility= View.GONE
        tViewTime.text =  "${(duracion.sum() - duracion.last())/1000}.00s"
        findNavController(host).navigate(R.id.action_global_Pre)
        showInterestial()
    }
    private lateinit var mInterstitialAd: InterstitialAd
    public fun showInterestial() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.e(TAG, "The interstitial wasn't loaded yet.")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG,"onPause")

        handler.removeCallbacks(runM1)
        handler.removeCallbacks(runM2)
        handler.removeCallbacks(runM3)
        handler.removeCallbacks(runM4)
        handler.removeCallbacks(runM5)
        handler.removeCallbacks(runM6)

        handler.removeCallbacks(runL1)
        handler.removeCallbacks(runL2)
        handler.removeCallbacks(runL3)
        handler.removeCallbacks(runL4)
        handler.removeCallbacks(runL5)
        handler.removeCallbacks(runL6)

        destroy = true;
     }

    companion object{
        val duracion = listOf(
            6000,//1.Mójate las manos con suficiente agua
            6000,//2.Enjabónate y frota las palmas de las manos entrelanzándolas
            6000,//3.Frota la parte superior de las manos con las palmas entrelanzándolas
            6000,//4.Forma un candado con tus manos y frótalas
            6000,//5.Enjuágate bien las manos con abudante agua a chorros
            4000
        )
    }

    var destroy = false;


    val runL1 = Runnable {
        loader.visibility= View.GONE
        beep.start()
        findNavController(host).navigate(R.id.action_preStepsFragment_to_FirstFragment)
    }

    val runL2 = Runnable {
        beep.start()
        findNavController(host).navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    val runL3 = Runnable {
        beep.start()
        findNavController(host).navigate(R.id.action_SecondFragment_to_threeFragment)
    }

    val runL4 = Runnable {
        beep.start()
        findNavController(host).navigate(R.id.action_threeFragment_to_fourFragment)
    }


    val runL5 = Runnable {
        beep.start()
        findNavController(host).navigate(R.id.action_fourFragment_to_fiveFragment)
    }

    val runL6 = Runnable {
        beep.start()
        findNavController(host).navigate(R.id.action_fiveFragment_to_sixFragment)
    }

    val runM1 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje1))
    }

    val runM2 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje2))
    }
    val runM3 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje3))
    }
    val runM4 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje4))
    }
    val runM5 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje5))
    }
    val runM6 = Runnable {
        ttsManager.initQueue(getString(R.string.mensaje6))
    }

    val handler = Handler()
    
    private fun animatefragments() {

        //0->1
        handler.post(
            runM1
        )
        handler.postDelayed(
            runL1
        ,delay+(duracion.subList(0,0).sum()))

        //1->2
        handler.postDelayed(
            runM2
        ,delay+((duracion.subList(0,1))).sum() - 3000)
        handler.postDelayed(
            runL2
        ,delay+((duracion.subList(0,1))).sum())

        //2->3
        handler.postDelayed(
            runM3
        ,delay+(duracion.subList(0,2).sum()) - 2000)
        handler.postDelayed(
            runL3
        ,delay+(duracion.subList(0,2).sum()))

        //3->4
        handler.postDelayed(
            runM4
        ,delay+(duracion.subList(0,3).sum()) - 3000)
        handler.postDelayed(
            runL4
        ,delay+(duracion.subList(0,3).sum()))

        //4->5
        handler.postDelayed(
            runM5
        ,delay+(duracion.subList(0,4).sum()) - 2500)
        handler.postDelayed(
            runL5
        ,delay+(duracion.subList(0,4).sum()))

        //5->6
        handler.postDelayed(
            runM6
        ,delay+(duracion.subList(0,5).sum()) - 2500)
        handler.postDelayed(
            runL6
        ,delay+(duracion.subList(0,5).sum()))
    }

    val TAG= MainActivity::class.java.simpleName
    private fun animateCounter(delay: Long) {
        handler.postDelayed({
            Thread {
                val startTime: Date  = Calendar.getInstance().time
                val totalTime = duracion.sum() - duracion.last()
                while (true){
                    val currentTime: Date = Calendar.getInstance().time
                    val diff = currentTime.time - startTime.time
                    val complement = totalTime - diff
                    val compSeconds: Int = (complement / 1000).toInt()
                    val compMiliSeconds: Int = (complement - compSeconds).toInt() % 1000
                    val compSecString = String.format("%02d", compSeconds);
                    val compMilString = String.format("%03d", compMiliSeconds);
                    model!!.postSeconds("${compSecString}.${compMilString}")
                    if (totalTime < diff) {
                        model!!.postSeconds("00.00")
                        break
                    }
                    if(destroy){
                        destroy = false
                        break
                    }
                }
        }.start()},delay)
    }
    val delay: Long = 3200
    val host = R.id.nav_host_fragment
    val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(delay)
    fun startWash() {

        loader.visibility= View.VISIBLE
        handler.post {
            animator.addUpdateListener { valueAnimator ->
                lottie.progress = valueAnimator.animatedValue as Float
            }
            animator.start()
        }

        animatefragments()
        animateCounter(delay)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {

    }
    override fun onDestroy(): kotlin.Unit {
        super.onDestroy()
        ttsManager.shutDown()
    }
}
