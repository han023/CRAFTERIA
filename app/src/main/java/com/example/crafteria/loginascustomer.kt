package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityLoginascustomerBinding
import com.example.crafteria.databinding.ActivityRegistarasasellerBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class loginascustomer : AppCompatActivity() {

    private lateinit var binding: ActivityLoginascustomerBinding
    lateinit var parentLayout:View;
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginascustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById<View>(android.R.id.content);

        binding.loginascustomer.setOnClickListener{

            val email = binding.loginascustemail.text.toString().trim()
            val pass = binding.loginascustpassword.text.toString().trim()


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

        binding.registarascustomer.setOnClickListener{
            startActivity(Intent(this@loginascustomer, registarasaseller::class.java))
        }


    }
}