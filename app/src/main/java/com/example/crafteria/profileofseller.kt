package com.example.crafteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.crafteria.databinding.ActivityLoginAsSellerBinding
import com.example.crafteria.databinding.ActivityProfileofsellerBinding
import com.example.crafteria.helpers.constants

class profileofseller : AppCompatActivity() {

    private lateinit var binding: ActivityProfileofsellerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileofsellerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Glide
            .with(this)
            .load("https://goo.gl/gEgYUd")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.profileofsellerimage);


        binding.ownproductdetail.setOnClickListener{

        }

        binding.recentaddproductdetails.setOnClickListener{

        }

        binding.viewrating.setOnClickListener{

        }

        binding.accountsetting.setOnClickListener{

        }


        binding.next.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.logout.setOnClickListener{
            constants.auth.signOut()
        }


    }
}