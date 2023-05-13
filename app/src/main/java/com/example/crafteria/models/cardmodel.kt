package com.example.crafteria.models

// data model for stroing and receiving data from firebase
data class cardmodel(
    val name: String,
    val cardnumber: String,
    val cvv: String,
    val exp: String,
    ):java.io.Serializable{
    constructor() : this(
        name = "",
        cardnumber = "",
        cvv = "",
        exp = "",
    )
    }
