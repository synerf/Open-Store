package com.synerf.openstore.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.synerf.openstore.R
import com.synerf.openstore.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        // when clicked on login, go to login activity
        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }

        // when clicked on Register button
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    /**
     * function to setup action bar
     */
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }

        binding.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * function to validate the entries of new user
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim{ it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(binding.etLastName.text.toString().trim{ it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }
            TextUtils.isEmpty(binding.etEmail.text.toString().trim{ it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim{ it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim{ it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }
            binding.etPassword.text.toString().trim{it<= ' '} != binding.etConfirmPassword.text.toString().trim{it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
//                showErrorSnackBar(resources.getString(R.string.register_success), false)
                true
            }
        }
    }

    /**
     * function to register user
     */
    private fun registerUser() {
        // check if inout is valid
        if (validateRegisterDetails()) {
            // show progress dialog
            showProgressDialog(resources.getString(R.string.please_wait))

            // get values from editText
            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            // create user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // hide progress dialog
                    hideProgressDialog()

                    // if user is registered successfully
                    if (task.isSuccessful) {
                        // firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        showErrorSnackBar("You are registered successfully. Your user id is ${firebaseUser.uid}",
                            false)

                        FirebaseAuth.getInstance().signOut()
                        finish()

                    } else {
                        // if registering is not successful
                        showErrorSnackBar(task.exception!!.message.toString(), false)
                    }
                }
            }
    }

}