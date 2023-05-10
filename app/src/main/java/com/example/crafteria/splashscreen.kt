package com.example.crafteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crafteria.databinding.ActivityLoginAsSellerBinding
import com.example.crafteria.databinding.ActivitySplashscreenBinding
import com.example.crafteria.helpers.constants
import com.google.firebase.database.FirebaseDatabase

class splashscreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val currentUser = constants.auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.cust.setOnClickListener{
            startActivity(Intent(this, loginascustomer::class.java))
        }

        binding.sell.setOnClickListener{
            startActivity(Intent(this, LoginAsSeller::class.java))
        }


    }
}