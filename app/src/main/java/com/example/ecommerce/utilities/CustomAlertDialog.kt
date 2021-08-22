package com.example.ecommerce.utilities

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.example.ecommerce.R

object CustomAlertDialog {

    fun showAlertDialog(context: Context,view: View) {

        val builderDialog = AlertDialog.Builder(context)
        builderDialog.setView(view)

        val dialog = builderDialog.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
    }
}