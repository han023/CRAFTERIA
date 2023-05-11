package com.example.crafteria.models

data class ordermodel(
    val name: String,
    val cardnumber: String,
    val cvv: String,
    val exp: String,
    val img: String,
    val title:String,
    val price:String,
    val quantity:String
):java.io.Serializable{
    constructor() : this(
        name = "",
        cardnumber = "",
        cvv = "",
        exp = "",
        img="",
        title="",
        price="",
        quantity=""
    )
}
