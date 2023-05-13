package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.crafteria.databinding.ActivityFinalcheckingBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.ordermodel
import com.example.crafteria.models.subcatmodel
import com.google.android.material.snackbar.Snackbar

class FinalChecking : AppCompatActivity() {

    private lateinit var binding: ActivityFinalcheckingBinding  // Binding object for the activity's layout.
    private lateinit var parentLayout: View  // View representing the parent layout of the activity.
    lateinit var sharedPreferences: SharedPreferences  // SharedPreferences object for storing and accessing data.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  // Call the superclass implementation of onCreate().

        binding = ActivityFinalcheckingBinding.inflate(layoutInflater)  // Inflate the activity's layout using the generated binding class.
        setContentView(binding.root)  // Set the root view of the inflated layout as the content view for the activity.

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)  // Get a reference to the SharedPreferences object with the specified name and mode.

        parentLayout = findViewById(android.R.id.content)  // Find the root view of the activity's layout using its resource ID (android.R.id.content) and assign it to the parentLayout variable.

        val data = intent.getSerializableExtra("data") as subcatmodel  // Retrieve the serialized extra data from the intent using the specified key ("data") and cast it to the subcatmodel type. Assign the result to the data variable.

        val carddata = intent.getSerializableExtra("card") as cardmodel  // Retrieve the serialized extra data from the intent using the specified key ("card") and cast it to the cardmodel type. Assign the result to the carddata variable.

        binding.exp.text = carddata.exp  // Set the text of the exp TextView in the binding object to the expiration date retrieved from carddata.

        binding.cardnumber.text = carddata.cardnumber  // Set the text of the cardnumber TextView in the binding object to the card number retrieved from carddata.

        binding.name.text = carddata.name  // Set the text of the name TextView in the binding object to the cardholder's name retrieved from carddata.

        binding.title.text = data.title  // Set the text of the title TextView in the binding object to the title retrieved from data.

        binding.price.text = data.price  // Set the text of the price TextView in the binding object to the price retrieved from data.

        Glide
            .with(this)  // Specify the context (this refers to the current activity) for loading the image.
            .load(data.img)  // Load the image from the specified URL or resource (data.img) into the ImageView.
            .centerCrop()  // Apply center cropping to the loaded image.
            .placeholder(R.drawable.cart)  // Set a placeholder image to be displayed while the actual image is being loaded.
            .into(binding.img)  // Specify the target ImageView (binding.img) to load the image into.



        binding.plus.setOnClickListener {
            val data1 = binding.quan.text.toString().toInt() + 1  // Increment the quantity by 1.
            var pr = binding.price.text.toString().toInt()  // Get the current price.
            pr += data.price.toInt()  // Add the price of the item to the current price.
            binding.quan.setText(data1.toString())  // Update the quantity displayed.
            binding.price.setText(pr.toString())  // Update the price displayed.
        }


        binding.min.setOnClickListener {
            var data1 = binding.quan.text.toString().toInt()  // Get the current quantity.
            if (data1 > 1) {  // Check if the quantity is greater than 1.
                var pr = binding.price.text.toString().toInt()  // Get the current price.
                pr -= data.price.toInt()  // Subtract the price of the item from the current price.
                data1 -= 1  // Decrement the quantity by 1.
                binding.quan.setText(data1.toString())  // Update the quantity displayed.
                binding.price.setText(pr.toString())  // Update the price displayed.
            }
        }


        binding.addtocart.setOnClickListener {
            // Create an instance of the order model with the necessary details.
            val ordermodel = ordermodel(
                carddata.name,
                carddata.cardnumber,
                carddata.cvv,
                carddata.exp,
                data.img,
                data.title,
                data.price,
                binding.quan.text.toString().trim()
            )

            // Generate a unique key for the order in the database.
            val key = constants.database.child("order")
                .child(sharedPreferences.getString("mobile", "").toString()).push()

            // Set the order model as the value at the generated key.
            key.setValue(ordermodel).addOnCompleteListener {
                if (it.isSuccessful) {
                    // If the order is successfully added to the database, navigate to the MainActivity.
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    Snackbar.make(parentLayout, "updated", Snackbar.LENGTH_SHORT).show()
                } else {
                    // If there is an error adding the order to the database, display an error message.
                    Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}