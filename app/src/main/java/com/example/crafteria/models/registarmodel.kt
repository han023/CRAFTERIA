package com.example.crafteria.models

data class registarmodel(
    val firstname: String,
    val lastname: String,
    val mobile: String,
    val email: String,
    val password : String,
    val address : String
    ){
    constructor() : this("", "","","","","")
}
