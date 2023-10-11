package com.synerf.openstore.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.synerf.openstore.R
import com.synerf.openstore.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
    }


    /**
     * In login screen, the clickable components are Login button, Forgot Password test and Register text
     */
    override fun onClick(v: View?) {
        if(v != null) {
            when(v.id) {
                R.id.tv_forgot_password -> {
                    startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
                }

                R.id.btn_login -> {
                    loginRegisteredUser()
                }

                R.id.tv_register -> {
                    // launch the register screen when the user clicks on the text
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


    /**
     * function to validate login details
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }


    /**
     * function login registered user to firebase
     */
    private fun loginRegisteredUser() {
        if(validateLoginDetails()) {
            // show progress dialog
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = binding.etEmail.text.toString().trim { it <= ' '}
            val password = binding.etPassword.text.toString().trim { it <= ' '}

            // Log in using firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // hide progress dialog
                    hideProgressDialog()
                    if(task.isSuccessful) {
                        showErrorSnackBar("You are logged in successfully.", false)
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}