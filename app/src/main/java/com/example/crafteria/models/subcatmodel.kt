package com.example.crafteria.models


// data model for stroing and receiving data from firebase
data class subcatmodel(
    var img: String, var title: String, var price:String
):java.io.Serializable{
    constructor() : this(
        img = "",
        title = "",
        price = ""
    )
}
