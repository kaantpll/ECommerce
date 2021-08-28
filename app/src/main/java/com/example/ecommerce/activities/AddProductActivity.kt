package com.example.ecommerce.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log

import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityAddProductBinding
import com.example.ecommerce.utilities.ErrorValidationSnackBar
import com.example.ecommerce.utilities.constant.Constants
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.type.DateTime
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.util.*


class AddProductActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth
    private lateinit var storage : FirebaseStorage
    private  lateinit var selectedImage  : Uri
    private lateinit var binding : ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSlider()

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        bindProgressButton(binding.productAddButton)





        val uuid = mAuth.currentUser?.uid


        binding.takePhotoIcon.setOnClickListener {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),111)
            }

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(takePictureIntent,5)

        }

        binding.productAddButton.setOnClickListener {
            showProgressCenter(binding.productAddButton)
            saveProductToDatabase(
                binding.productTitle.text.toString(),
                binding.productDescription.text.toString(),
                binding.productPrice.text.toString(),
                binding.productStock.text.toString(),
                binding.slider.value,
                uuid
            )
        }

    }

    private fun showProgressCenter(button: MaterialButton) {
        button.showProgress {
            progressColor = Color.WHITE
            gravity = DrawableButton.GRAVITY_CENTER
        }

        button.isEnabled = false
        Handler().postDelayed({
            button.isEnabled = true
            button.hideProgress("Loaded")
        }, 2000)
    }

    private fun saveProductToDatabase
            (title: String,
             description: String,
             price: String,
             stock: String,
             value: Float,
              user_id : String?)
    {
            val uniqueID = UUID.randomUUID()
            val imageName = "${uniqueID}.jpg"
            val reference  =storage.reference
            val pictureReference = reference.child("images").child(imageName)

            val shareDate = DateTime.HOURS_FIELD_NUMBER


            pictureReference.putFile(selectedImage).addOnSuccessListener {
                val loadedPictureRef = storage.reference.child("images").child(imageName)
                loadedPictureRef.downloadUrl.addOnSuccessListener { uri->
                    val url = uri.toString()

                    val product = hashMapOf(
                            "productTitle" to title,
                            "productDescription" to description,
                            "price" to price,
                            "stock" to stock,
                            "discountValue" to value.toString(),
                            "user_id" to user_id,
                            "shareDate" to shareDate,
                            "imageUrl" to url
                    )
                    db.collection(Constants.PRODUCT_COLLECTION_NAME)
                            .add(product).addOnSuccessListener { task->
                            binding.productAddButton.isEnabled = false

                    }
                }

            }
        }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 5 && resultCode == Activity.RESULT_OK){

             val takenImage = data?.extras?.get("data") as Bitmap
             Glide.with(this).load(takenImage).into(binding.takePhoto)

            val bytes = ByteArrayOutputStream()
            takenImage.compress(Bitmap.CompressFormat.JPEG,100,bytes)
            val path = MediaStore.Images.Media.insertImage(this.contentResolver,takenImage,"Title",null)
            selectedImage = Uri.parse(path.toString())
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setSlider() {
        binding.slider.setLabelFormatter { value : Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
    }
}

