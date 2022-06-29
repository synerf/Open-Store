package com.synerf.openstore.activities

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowInsets
import android.view.WindowManager
import com.synerf.openstore.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // to hide status bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // go to login activity after 2.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        },2500)

//        // custom font
//        val typeface: Typeface = Typeface.createFromAsset(assets, "Montserrat-Bold.ttf")
//
//        // set custom font to tv_app_name
//        binding.tvAppName.typeface = typeface
    }
}