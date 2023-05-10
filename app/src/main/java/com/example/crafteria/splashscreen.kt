package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivitySplashscreenBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class splashscreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    lateinit var parentLayout:View;
    lateinit var sharedPreferences :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentLayout = findViewById<View>(android.R.id.content);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        runBlocking {
            delay(5000) // delay for 3 seconds

            if ( sharedPreferences.contains("mobile") && sharedPreferences.getString("mobile","").toString() != "" ) {
                val intent = Intent(this@splashscreen, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                val intent = Intent(this@splashscreen, loginascustomer::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        }


        binding.cust.setOnClickListener{
            val intent = Intent(this@splashscreen, loginascustomer::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }
}