package com.example.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.ecommerce.R
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        editText.setOnFocusChangeListener { view, focused ->
            if (focused) editText.setBackgroundResource(R.drawable.focus_border);

        }
        editText2.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) editText2.setBackgroundResource(R.drawable.focus_border)
        }
        editText3.setOnFocusChangeListener{view,hasFocus->
            if(hasFocus) editText3.setBackgroundResource(R.drawable.focus_border)
        }

        AlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
        }

        createAccountButton.setOnClickListener {
            val email = editText2.text.toString()
            val password = editText3.text.toString()

            if(validation(it)){
                createNewAccount(email,password)
            }
        }
    }

    private fun createNewAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful) {
                val intent = Intent(this,LogInActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun validation(view : View) : Boolean{
        return when{
            TextUtils.isEmpty(editText.text.toString().trim(){it <= ' '})->{
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Name is not empty !!! ",true)
                false
            }
            TextUtils.isEmpty(editText2.text.toString().trim(){it <= ' '}) || editText2.text.toString().contains("@",ignoreCase = true)-> {
                ErrorValidationSnackBar.showErrorSnackBar(this, view, "Email can't be short", true)
                false
            }
            TextUtils.isEmpty(editText3.text.toString().trim(){it <= ' ' || editText3.text.length <=6})->{
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Password can't be than less 6 ",true)
                false
            }

            else -> {
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Success",false)
                false
            }
        }

    }
}