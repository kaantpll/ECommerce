package com.example.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.example.ecommerce.R
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import com.example.ecommerce.utilities.CustomAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        val dialogView = View.inflate(this,R.layout.custom_success_dialog_alert,null)

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
                createNewAccount(email,password,dialogView)
            }
        }
    }

    private  fun createNewAccount(email: String, password: String,view : View) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful) {
                CustomAlertDialog.showAlertDialog(this,view)

                val user : FirebaseUser= task.result!!.user!!

                Handler().postDelayed({
                    val intent = Intent(this,FeedActivity::class.java)
                    startActivity(intent)
                }, 3000)
            }
        }

    }

    private fun validation(view : View) : Boolean{
        return when{
            TextUtils.isEmpty(editText.text.toString().trim(){it <= ' '})->{
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Name is not empty !!! ",true)
                false
            }
            TextUtils.isEmpty(editText2.text.toString().trim(){it <= ' '}) || !editText2.text.toString().contains("@",ignoreCase = true)-> {
                ErrorValidationSnackBar.showErrorSnackBar(this, view, "Email can't be short", true)
                false
            }
            TextUtils.isEmpty(editText3.text.toString().trim(){it <= ' ' || editText3.text.length <=6})->{
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Password can't be than less 6 ",true)
                false
            }
            else -> {
                ErrorValidationSnackBar.showErrorSnackBar(this,view,"Success",false)
                true
            }
        }

    }
}