
package com.example.crafteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityMainBinding
import com.example.crafteria.databinding.ActivityRegistarasasellerBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar

class registarasaseller : AppCompatActivity() {

    private lateinit var binding: ActivityRegistarasasellerBinding
    val parentLayout = findViewById<View>(android.R.id.content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarasasellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra("type").toString()
        if (data == "seller"){
            binding.registartext.text = "Registar as a Seller";
        }else{
            binding.registartext.text = "Registar as a Customer";
        }



        binding.registarasseller.setOnClickListener{

            val firstname = binding.registarasasellerfname.text.toString().trim()
            val lastname = binding.registarasasellerlname.text.toString().trim()
            val mobile = binding.registarasasellerno.text.toString().trim()
            val email = binding.registarasaselleremail.text.toString().trim()
            val password = binding.registarasasellerpassword.text.toString().trim()
            val confirmpass = binding.registarasasellerconpassword.text.toString().trim()

            if (firstname.isEmpty() && lastname.isEmpty() && mobile.isEmpty() && email.isEmpty() && password.isEmpty() && confirmpass.isEmpty()){
                Snackbar.make(parentLayout, "Fill All Fields", Snackbar.LENGTH_SHORT).show()
            } else if (mobile.length != 11){
                Snackbar.make(parentLayout, "Mobile number not correct", Snackbar.LENGTH_SHORT).show()
            } else if (password != confirmpass){
                Snackbar.make(parentLayout, "Password does not match", Snackbar.LENGTH_SHORT).show()
            } else if (!email.contains('@')){
                Snackbar.make(parentLayout, "check your email", Snackbar.LENGTH_SHORT).show()
            }else{


                constants.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            constants.database.child("user").child(constants.auth.currentUser.toString())
                                .setValue(registarmodel(firstname,lastname,mobile,email,password,data)).addOnCompleteListener{
                                    if(task.isSuccessful){
                                        updateui(data)
                                    } else{
                                        Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                                    }
                                }

                        } else {
                            Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                        }
                    }


            }

        }


    }

    fun updateui(type:String){
        if (type == "seller"){
            startActivity(Intent(this, profileofseller::class.java))
        }else{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}