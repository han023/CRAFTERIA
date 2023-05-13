package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityUpdateCartBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar

class UpdateProfile : AppCompatActivity() {
    // class variables

    lateinit var binding : ActivityUpdateCartBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var parentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // preferences variable

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)


        val data = intent.getSerializableExtra("data") as registarmodel
        // update values
        binding.registarasasellerfname.setText(data.firstname)
        binding.registarasasellerlname.setText(data.lastname)
        binding.registarasasellerno.setText(data.mobile)

       // take action on register button ... register as seller is actually, customer
        binding.registarasseller.setOnClickListener {
            // storing data in variables
            val firstname = binding.registarasasellerfname.text.toString().trim()
            val lastname = binding.registarasasellerlname.text.toString().trim()
            val mobile = binding.registarasasellerno.text.toString().trim()
            // update data on firebase
            constants.database.child("user").child( sharedPreferences.getString("mobile","").toString() )
                            .setValue(registarmodel(firstname,lastname,mobile,data.email,data.password,data.address)).addOnCompleteListener{
                                if(it.isSuccessful){
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)

                                    Snackbar.make(parentLayout, "updated", Snackbar.LENGTH_SHORT).show()

                                } else{
                                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                                }
                            }


            }

    }
}