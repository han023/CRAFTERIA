package com.example.crafteria.models

// data model for stroing and receiving data from firebase
data class categorymodel(
    val img: String, val title: String
    ){
    constructor() : this(
        img = "",
        title = "",
    )
}
