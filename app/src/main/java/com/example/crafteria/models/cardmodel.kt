package com.example.crafteria.models

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
