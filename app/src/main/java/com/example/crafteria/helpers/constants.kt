package com.example.crafteria.helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class constants {

    companion object{

        var auth: FirebaseAuth = Firebase.auth
        val database = Firebase.database.getReference()



    }
}