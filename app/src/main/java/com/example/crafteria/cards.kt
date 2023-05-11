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

    private lateinit var binding:ActivityCardsBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var catdata : ArrayList<cardmodel>
    private lateinit var adapter: cardadapter
    private lateinit var data:subcatmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        data = intent.getSerializableExtra("data") as subcatmodel

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)


        catdata = arrayListOf()
        getdata()


        binding.newcard.setOnClickListener{
            val intent = Intent(this,Newcard::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }

    }

    private fun getdata() {
        constants.database.child("card").child(sharedPreferences.getString("mobile","").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (catsnap in snapshot.children){
                            val cat = catsnap.getValue(cardmodel::class.java)
                            catdata.add(cat!!)
                        }
                        adapter = cardadapter(catdata,this@Cards,data)
                        binding.recyclerView.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}