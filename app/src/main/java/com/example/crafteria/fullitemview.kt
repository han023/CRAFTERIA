package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.crafteria.databinding.ActivityFullitemviewBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class fullitemview : AppCompatActivity() {

    private lateinit var binding:ActivityFullitemviewBinding
    lateinit var sharedPreferences : SharedPreferences
    lateinit var parentLayout: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullitemviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById<View>(android.R.id.content);
        val data = intent.getSerializableExtra("data") as subcatmodel

        Glide
            .with(this)
            .load(data.img)
            .centerCrop()
            .placeholder(R.drawable.cart)
            .into(binding.mainimg);

        binding.title.setText(data.title)
        binding.price.setText(data.price)

        binding.addtocart.setOnClickListener {
            val key = constants.database.child("cart").child( sharedPreferences.getString("mobile","").toString()).push()
                key.setValue(data).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    Snackbar.make(parentLayout, "Added sucessfully", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}