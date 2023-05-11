package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crafteria.databinding.ActivitySplashBinding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var parentLayout:View
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentLayout  = findViewById (android.R.id.content)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        runBlocking {
            delay(2000) // delay for 2 seconds

            if ( sharedPreferences.contains("mobile") &&
                sharedPreferences.getString("mobile","").toString() != "" )
            {
                val intent = Intent(this@Splash, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                val intent = Intent(this@Splash, Loginascustomer::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        }
    }
}