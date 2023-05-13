package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityLoginascustomerBinding
import com.example.crafteria.helpers.constants
import com.google.android.material.snackbar.Snackbar

class Loginascustomer : AppCompatActivity() {

    private lateinit var binding: ActivityLoginascustomerBinding
    private lateinit var parentLayout:View
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

// Call the superclass's onCreate() method to perform necessary initialization.
        super.onCreate(savedInstanceState)

// Inflate the activity layout using the ActivityLoginascustomerBinding.
        binding = ActivityLoginascustomerBinding.inflate(layoutInflater)

// Set the root view of the activity layout as the content view.
        setContentView(binding.root)

// Get the SharedPreferences instance for "MyPrefs" with private mode.
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

// Find the root view of the activity layout using its ID "android.R.id.content".
// This view will be used as the parent layout for displaying Snackbars.
        parentLayout = findViewById(android.R.id.content)

        binding.loginascustomer.setOnClickListener{
            // Get the text entered in the loginascustemail EditText and remove any leading or trailing whitespace.
            val email = binding.loginascustemail.text.toString().trim()

// Get the text entered in the loginascustpassword EditText and remove any leading or trailing whitespace.
            val pass = binding.loginascustpassword.text.toString().trim()

// Obtain the email and password entered by the user for authentication.

        // validations
            if (email.isEmpty() || pass.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            } else if(!email.contains('@')){
                Snackbar.make(parentLayout, "check your email", Snackbar.LENGTH_SHORT).show()
            } else{

                constants.auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            val editor = sharedPreferences.edit()
                            editor.putString("mobile", email.replace("@","")
                                .replace(".","").replace("#",""))
                            editor.apply()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                        }
                    }

            }

        }
        //register as a customer
        binding.registarascustomer.setOnClickListener{
            startActivity(Intent(this@Loginascustomer, Registarasaseller::class.java))
        }


    }
}