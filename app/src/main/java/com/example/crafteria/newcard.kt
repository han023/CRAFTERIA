package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityNewcardBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class Newcard : AppCompatActivity() {

    private lateinit var binding : ActivityNewcardBinding
    private lateinit var parentLayout: View
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)

        var data = subcatmodel()
        if(intent.getSerializableExtra("data") != null) {
            data = intent.getSerializableExtra("data") as subcatmodel
        }

        binding.add.setOnClickListener {

            val name = binding.name.text.toString().trim()
            val number = binding.number.text.toString().trim()
            val cvv = binding.cvv.text.toString().trim()
            val exp = binding.exp.text.toString().trim()

            if (name.isEmpty() || number.isEmpty()|| cvv.isEmpty() || exp.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            } else if(cvv.length != 3){
                Snackbar.make(parentLayout, "cvv number is only 3 digits", Snackbar.LENGTH_SHORT).show()
            } else if(number.length != 16 ){
                Snackbar.make(parentLayout, "card number is 16 digits", Snackbar.LENGTH_SHORT).show()
            } else if(exp.length != 4){
                Snackbar.make(parentLayout, "year is not correct", Snackbar.LENGTH_SHORT).show()
            } else if(exp.toInt() < 2023){
                Snackbar.make(parentLayout, "exp date cannot be less than current year", Snackbar.LENGTH_SHORT).show()
            }else{
                val cardmodel = cardmodel(name, number, cvv, exp)

                val key = constants.database.child("card").child(sharedPreferences.getString("mobile","").toString()).push()
                key.setValue(cardmodel).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this,Cards::class.java)
                        intent.putExtra("data", data)
                        startActivity(intent)

                        Snackbar.make(parentLayout, "added", Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                    }
                }

            }



        }

    }
}