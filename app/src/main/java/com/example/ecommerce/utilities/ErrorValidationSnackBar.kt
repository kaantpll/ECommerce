package com.example.ecommerce.utilities

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ecommerce.R
import com.google.android.material.snackbar.Snackbar

 object ErrorValidationSnackBar {


        fun showErrorSnackBar(context : Context, view : View, message:String, errorMessage:Boolean){
            val snackBar = Snackbar.make(view,message,Snackbar.LENGTH_LONG)
            val snackBarView = snackBar.view


            if(errorMessage){
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.errorRegister
                    )
                )
            }else{
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.successRegister
                    )
                )
            }

            snackBar.show()
        }
    }
