package com.example.crafteria

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.crafteria.databinding.ActivityFullitemviewBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class FullItemView : AppCompatActivity() {

    private lateinit var binding: ActivityFullitemviewBinding // Holds the references to views in the activity's layout using View Binding.

    private lateinit var sharedPreferences: SharedPreferences // Used to store and retrieve shared preferences data.

    private lateinit var parentLayout: View // Represents the root view of the activity's layout.


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the superclass onCreate method to perform necessary initialization.
        super.onCreate(savedInstanceState)
        // Inflate the layout for this activity using the ActivityFullitemviewBinding.
        binding = ActivityFullitemviewBinding.inflate(layoutInflater)

        // Set the content view of the activity to the root view of the binding.
        setContentView(binding.root)

        // The activity layout is inflated and set as the content view.

       // Get the SharedPreferences instance for "MyPrefs" with private mode.
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

// Find the root view of the activity layout using its ID "android.R.id.content".
// This view will be used as the parent layout for displaying Snackbars.
        parentLayout = findViewById(android.R.id.content)

// Get the serialized "data" object from the intent and cast it to the subcatmodel class.
        val data = intent.getSerializableExtra("data") as subcatmodel

        Glide
            .with(this)
            .load(data.img)
            .centerCrop()
            .placeholder(R.drawable.cart)
            .into(binding.mainimg)
        // setting text of title and price

        binding.title.text = data.title
        binding.price.text = data.price

        binding.addtocart.setOnClickListener {
            val key = constants.database.child("cart").child( sharedPreferences.getString("mobile","").toString()).push()
                key.setValue(data).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    Snackbar.make(parentLayout, "Added successfully", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}