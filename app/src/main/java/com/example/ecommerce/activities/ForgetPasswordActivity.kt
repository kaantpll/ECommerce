package com.example.ecommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.ecommerce.R
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        mAuth = FirebaseAuth.getInstance()

        resetPasswordForEmail.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                if(resetPasswordForEmail.text.toString().contains('@' , ignoreCase = true)){
                    resetPasswordForEmail.setBackgroundResource(R.drawable.focus_border_verify)
                }else{
                    resetPasswordForEmail.setBackgroundResource(R.drawable.focus_border)
                }

            }
        }

       sendEmail.setOnClickListener {

           val email = resetPasswordForEmail.text.toString()
           sendResetEmail(email)
       }


    }

    private fun sendResetEmail(email : String) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task->
           if(task.isSuccessful){
               Toast.makeText(this,"Send link to your email",Toast.LENGTH_SHORT).show()
               finish()
           }
        }.addOnFailureListener { e->
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()

        }
    }


}