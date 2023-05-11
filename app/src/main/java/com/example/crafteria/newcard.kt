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

        val data = intent.getSerializableExtra("data") as subcatmodel
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)

        binding.add.setOnClickListener {

            val name = binding.name.text.toString()
            val number = binding.number.text.toString()
            val cvv = binding.cvv.text.toString()
            val exp = binding.exp.text.toString()

            if (name.isEmpty() || number.isEmpty()|| cvv.isEmpty() || exp.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
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