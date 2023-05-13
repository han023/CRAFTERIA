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
    // class variables


    // Declare a variable to hold the reference to the ActivitySplashBinding object.
    private lateinit var binding: ActivitySplashBinding

    // Declare a variable to hold the reference to the parent view.
    private lateinit var parentLayout: View

    // Declare a variable to hold the reference to the SharedPreferences object.
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding object, which is responsible for binding views in the activity.
        binding = ActivitySplashBinding.inflate(layoutInflater)

        // Set the content view of the activity to the root view of the binding object.
        setContentView(binding.root)

        // Obtain a reference to the parent view of the activity using its ID (android.R.id.content).
        parentLayout = findViewById(android.R.id.content)

        // Obtain the SharedPreferences object with the name "MyPrefs" and private mode. This will be used to store and retrieve shared preferences data.
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }catch (_:Exception){

        }
        // Enable Firebase Database persistence. This allows the Firebase Database t
        // o store a copy of the data locally on the device, providing offline access
        // and improving app performance when the device is offline or has limited connectivity.

        runBlocking {
            delay(2000) // delay for 2 seconds

            if ( sharedPreferences.contains("mobile") &&
                sharedPreferences.getString("mobile","").toString() != "" )
            {  // moving to main activity
                val intent = Intent(this@Splash, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                // moving to login as customer activity
                val intent = Intent(this@Splash, Loginascustomer::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        }
    }
}