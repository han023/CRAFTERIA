package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.crafteria.databinding.ActivityUpdateaddressBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar

class updateaddress : AppCompatActivity() {
    // class variables

    // Declare a variable for the binding of the activity_updateaddress layout.
    private lateinit var binding: ActivityUpdateaddressBinding

    // Declare a variable for accessing the shared preferences.
    lateinit var sharedPreferences: SharedPreferences

    // Declare a variable for the parent layout of the activity.
    private lateinit var parentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateaddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)

        val data = intent.getSerializableExtra("data") as registarmodel

        Log.e("123456",data.address)
        if (data.address != ""){
            val string = data.address
            val list = string.split(",@#", ".")
            // setting tests for lines of address, postal code and city
            binding.line1.setText(list[0])
            binding.line2.setText(list[1])
            binding.postal.setText(list[2])
            binding.city.setText(list[3])
        }


        binding.registarasseller.setOnClickListener {
            // setting texts in case of registering
            val line1 = binding.line1.text.toString().trim()
            val line2 = binding.line2.text.toString().trim()
            val postal = binding.postal.text.toString().trim()
            val city = binding.city.text.toString().trim()

           // validations
            if (line1.isEmpty() || line2.isEmpty() || postal.isEmpty() || city.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            } else if(postal.length != 5){
                Snackbar.make(parentLayout, "check postal code", Snackbar.LENGTH_SHORT).show()
            } else {

                constants.database.child("user")
                    .child(sharedPreferences.getString("mobile", "").toString()).child("address")
                    .setValue(line1 + ",@#" + line2 + ",@#" + postal + ",@#" + city)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)

                            Snackbar.make(parentLayout, "updated", Snackbar.LENGTH_SHORT).show()

                        } else {
                            Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
            }


        }

    }
}