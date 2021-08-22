package com.example.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.ecommerce.R
import com.example.ecommerce.utilities.CustomAlertDialog
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()

        val dialogView = View.inflate(this,R.layout.loading_dialog,null)

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
            val email = editText2.text.toString()
            val password = editText3.text.toString()
            if(validation(it)){
                logInUser(email,password,dialogView)
            }
        }

        forgetPassword.setOnClickListener {
            val intent = Intent(this,ForgetPasswordActivity::class.java)
            startActivity(intent)
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
                true
            }
        }
    }

    private fun logInUser(email: String, password: String,view : View) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                CustomAlertDialog.showAlertDialog(this,view)

                Handler().postDelayed({
                    val intent = Intent(this,FeedActivity::class.java)
                    startActivity(intent)
                }, 2000)
            }
        }.addOnFailureListener { e->
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
}