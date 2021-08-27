package com.example.ecommerce.model

data class Product(
     val id : Int,
     val description : String,
     val image : String,
     val price : Int,
     val stock_quantity : Int,
     val title : String,
     val user_id : String,
     val user_name : String,
)