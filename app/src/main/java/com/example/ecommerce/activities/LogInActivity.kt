package com.example.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.ecommerce.R
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        editText2.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) editText2.setBackgroundResource(R.drawable.focus_border)
        }
        editText3.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) editText3.setBackgroundResource(R.drawable.focus_border)
        }

        goToRegister.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        logInButton.setOnClickListener {
            validation(it)
        }


    }

    private fun validation(it: View) : Boolean {
        return when {
            TextUtils.isEmpty(editText2.text.toString().trim() { it <= ' ' }) -> {
                ErrorValidationSnackBar.showErrorSnackBar(this,it,"Email is not empty",true)
                false
            }
            TextUtils.isEmpty(editText3.text.toString().trim() { it <= ' ' }) -> {
                ErrorValidationSnackBar.showErrorSnackBar(this,it,"Password is not empty",true)
                false
            }
            else ->{  ErrorValidationSnackBar.showErrorSnackBar(this,it,"Success",false)
                false
            }
        }
    }
}