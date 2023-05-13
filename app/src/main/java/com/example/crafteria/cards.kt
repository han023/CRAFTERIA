package com.example.crafteria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crafteria.adapters.cardadapter
import com.example.crafteria.databinding.ActivityCardsBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.subcatmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Cards : AppCompatActivity() {
   // class variables related to different classes
    private lateinit var binding: ActivityCardsBinding  // Reference to the binding object for the activity_cards.xml layout.
    lateinit var sharedPreferences: SharedPreferences  // Reference to the SharedPreferences object for storing data.
    private lateinit var catdata: ArrayList<cardmodel>  // ArrayList to hold the card data.
    private lateinit var adapter: cardadapter  // Adapter for displaying the card data.
    private lateinit var data: subcatmodel  // Variable to hold the selected subcategory data.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) // Initialize the SharedPreferences object with the specified name and mode.
        data = intent.getSerializableExtra("data") as subcatmodel // Get the serialized subcatmodel object passed from the previous activity.

        binding.recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager for the RecyclerView to a LinearLayoutManager.
        binding.recyclerView.setHasFixedSize(true) // Set that the RecyclerView has a fixed size.


        catdata = arrayListOf() // making arrayList variable
        getdata()

        // function to pass data from cards to New card class

        binding.newcard.setOnClickListener{
            val intent = Intent(this,Newcard::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }

    }
    // function to get data from data base
    private fun getdata() {
        constants.database.child("card").child(sharedPreferences.getString("mobile","").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){  // in case condition is true then execute below body
                        val key = ArrayList<String>()
                        catdata.clear()
                        for (catsnap in snapshot.children){
                            key.add(catsnap.key.toString());
                            val cat = catsnap.getValue(cardmodel::class.java)
                            catdata.add(cat!!)
                        }
                        adapter = cardadapter(catdata,this@Cards,data,key)
                        binding.recyclerView.adapter = adapter
                    }
                }
               // no need of it currently in our project
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}