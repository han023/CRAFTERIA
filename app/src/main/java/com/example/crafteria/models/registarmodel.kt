package com.example.crafteria.models

import java.io.Serializable

data class registarmodel(
    val firstname: String,
    val lastname: String,
    val mobile: String,
    val email: String,
    val password : String,
    val address : String
    )  : Serializable {
    constructor() : this(
        firstname = "",
        lastname = "",
        mobile = "",
        email = "",
        password = "",
        address = ""
    )
}
