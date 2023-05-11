package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.crafteria.databinding.ActivityFinalcheckingBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.ordermodel
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class finalchecking : AppCompatActivity() {

    private lateinit var binding:ActivityFinalcheckingBinding
    lateinit var parentLayout: View;
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalcheckingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById<View>(android.R.id.content);

        val data = intent.getSerializableExtra("data") as subcatmodel
        val carddata = intent.getSerializableExtra("card") as cardmodel

        binding.exp.setText(carddata.exp)
        binding.cardnumber.setText(carddata.cardnumber)
        binding.name.setText(carddata.name)

        binding.title.setText(data.title)
        binding.price.setText(data.price)
        Glide
            .with(this)
            .load(data.img)
            .centerCrop()
            .placeholder(R.drawable.cart)
            .into(binding.img);


        binding.addtocart.setOnClickListener {
            val ordermodel = ordermodel(
                carddata.name,
                carddata.cardnumber,
                carddata.cvv,
                carddata.exp,
                data.img,
                data.title,
                data.price,
                binding.quantity.text.toString().trim()
            )
            val key = constants.database.child("order")
                .child(sharedPreferences.getString("mobile", "").toString()).push()
            key.setValue(ordermodel).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    Snackbar.make(parentLayout, "updated", Snackbar.LENGTH_SHORT).show()

                } else {
                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}