package com.example.crafteria.models

import java.io.Serializable

// data model for stroing and receiving data from firebase
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
