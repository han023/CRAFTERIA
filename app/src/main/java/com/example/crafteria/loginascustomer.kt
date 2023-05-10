package com.example.crafteria

import android.content.Intent
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
    val parentLayout = findViewById<View>(android.R.id.content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginascustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginascustomer.setOnClickListener{

            val email = binding.loginascustemail.text.toString().trim()
            val pass = binding.loginascustpassword.text.toString().trim()


            if (email.isEmpty() && pass.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            } else if(!email.contains('@')){
                Snackbar.make(parentLayout, "check your email", Snackbar.LENGTH_SHORT).show()
            } else{

                constants.auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            constants.database.child("user").child(constants.auth.currentUser.toString())
                                .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    dataSnapshot.getValue(registarmodel::class.java)
                                    startActivity(Intent(this@loginascustomer, MainActivity::class.java))

                                }
                                override fun onCancelled(error: DatabaseError) {
                                    // Failed to read value
                                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                                }
                            })

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                        }
                    }

            }

        }


        binding.loginascustforgetpassword.setOnClickListener{

        }

        binding.registarascustomer.setOnClickListener{
            val intent = Intent(this, registarasaseller::class.java)
            intent.putExtra("type", "customer")
            startActivity(intent)
        }


    }
}