
package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityRegistarasasellerBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar

class Registarasaseller : AppCompatActivity() {

    private lateinit var binding: ActivityRegistarasasellerBinding
    private lateinit var parentLayout:View
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarasasellerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parentLayout = findViewById(android.R.id.content)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.registarasseller.setOnClickListener{

            // stroing data in variables
            val firstname = binding.registarasasellerfname.text.toString().trim()
            val lastname = binding.registarasasellerlname.text.toString().trim()
            val mobile = binding.registarasasellerno.text.toString().trim()
            val email = binding.registarasaselleremail.text.toString().trim()
            val password = binding.registarasasellerpassword.text.toString().trim()
            val confirmpass = binding.registarasasellerconpassword.text.toString().trim()

            if (firstname.isEmpty() || lastname.isEmpty() || mobile.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmpass.isEmpty()){
                Snackbar.make(parentLayout, "Fill All Fields", Snackbar.LENGTH_SHORT).show()
            } else if (mobile.length != 12){
                Snackbar.make(parentLayout, "Mobile number not correct", Snackbar.LENGTH_SHORT).show()
            } else if(password.length < 6){
                Snackbar.make(parentLayout, "Password atlest 6 character long", Snackbar.LENGTH_SHORT).show()
            } else if (password != confirmpass){
                Snackbar.make(parentLayout, "Password does not match", Snackbar.LENGTH_SHORT).show()
            } else if (!email.contains('@')){
                Snackbar.make(parentLayout, "check your email", Snackbar.LENGTH_SHORT).show()
            }else{


                constants.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            val editor = sharedPreferences.edit()
                            editor.putString("mobile", email.replace("@","")
                                .replace(".","").replace("#",""))
                            editor.apply()


                            constants.database.child("user").child( sharedPreferences.getString("mobile","").toString() )
                                .setValue(registarmodel(firstname,lastname,"+94"+mobile,email,password,"")).addOnCompleteListener{
                                    if(task.isSuccessful){

                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)

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


}