package com.example.ecommerce.model

data class User(
    val id : String="",
    val email : String = "",
    val password : String = "",
    val name : String = "",
    val gender : String = "",
    val image : String = "",
    val mobile : String = "",
    val profileCompleted : Int = 0
)
