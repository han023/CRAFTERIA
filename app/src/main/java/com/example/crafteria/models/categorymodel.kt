package com.example.crafteria.models


data class categorymodel(
    val img: String, val title: String
    ){
    constructor() : this(
        img = "",
        title = "",
    )
}
