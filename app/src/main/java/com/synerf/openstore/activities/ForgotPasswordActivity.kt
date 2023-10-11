package com.synerf.openstore.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.synerf.openstore.R
import com.synerf.openstore.databinding.ActivityForgotPasswordBinding


class ForgotPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
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

        // setup action bar
        setupActionBar()

        binding.btnSubmit.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim { it <= ' '}
            if (email.isEmpty()) {
                Toast.makeText(this@ForgotPasswordActivity, "Please enter email.", Toast.LENGTH_SHORT).show()
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task->
                        hideProgressDialog()
                        if(task.isSuccessful) {
                            Toast.makeText(this@ForgotPasswordActivity, "Email sent to reset password!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@ForgotPasswordActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


    /**
     * function to setup action bar
     */
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }

        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}