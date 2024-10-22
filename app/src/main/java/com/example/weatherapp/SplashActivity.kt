package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // ربط ProgressBar و LottieAnimationView من XML
        progressBar = findViewById(R.id.progressBar)
        lottieAnimationView = findViewById(R.id.lottieAnimationView)

        // إظهار ProgressBar وبدء الرسوم المتحركة
        progressBar.visibility = ProgressBar.VISIBLE
        lottieAnimationView.playAnimation()

        // تأخير الانتقال إلى MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = ProgressBar.GONE // إخفاء ProgressBar
            lottieAnimationView.cancelAnimation() // إيقاف الرسوم المتحركة
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 6000)
    }
}
