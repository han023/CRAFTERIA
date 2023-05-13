package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivityUpdatecardBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class updatecard : AppCompatActivity() {
    // class variables

    private lateinit var binding:ActivityUpdatecardBinding
    private lateinit var parentLayout: View
    lateinit var sharedPreferences : SharedPreferences



    // functions section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatecardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting data
        val data = intent.getSerializableExtra("data") as subcatmodel
        val card = intent.getSerializableExtra("card") as cardmodel
        val keyy = intent.getStringExtra("key").toString()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)

        // setting data
        binding.name.setText(card.name)
        binding.exp.setText(card.exp)
        binding.number.setText(card.cardnumber)
       // calling add button
        binding.add.setOnClickListener {

            val name = binding.name.text.toString().trim()
            val number = binding.number.text.toString().trim()
            val exp = binding.exp.text.toString().trim()
            // validations for app

            if (name.isEmpty() || number.isEmpty() || exp.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            }  else if(number.length != 16 ){
                Snackbar.make(parentLayout, "card number is 16 digits", Snackbar.LENGTH_SHORT).show()
            } else if(exp.length != 4){
                Snackbar.make(parentLayout, "year is not correct", Snackbar.LENGTH_SHORT).show()
            } else if(exp.toInt() < 2023){
                Snackbar.make(parentLayout, "exp date cannot be less than current year", Snackbar.LENGTH_SHORT).show()
            }else{
                val cardmodel = cardmodel(name, number, card.cvv, exp)

                constants.database.child("card").child(sharedPreferences.getString("mobile","").toString()).child(keyy).
                setValue(cardmodel).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this,Cards::class.java)
                        intent.putExtra("data", data)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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