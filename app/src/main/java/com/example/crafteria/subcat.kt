package com.example.crafteria

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crafteria.adapters.subcatadapter
import com.example.crafteria.databinding.ActivitySubcatBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.subcatmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class SubCat : AppCompatActivity() {
    // class variables

    lateinit var binding: ActivitySubcatBinding
    private lateinit var catdata : ArrayList<subcatmodel>
    private lateinit var adapter: subcatadapter
    private lateinit var type:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = intent.getStringExtra("cat").toString()

        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)
                return true
            }
        })




        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.setHasFixedSize(true)


        catdata = arrayListOf()
        getdata()





    }

    private fun filterlist(newText: String?) {
        // filtering data with respect to search view
        if(newText != null){
            val filterlist = ArrayList<subcatmodel>()
            for(i in catdata){
                if (i.title.lowercase(Locale.ROOT).contains(newText)){
                    filterlist.add(i)
                }
            }

            if (filterlist.isNotEmpty()){
                adapter.setfilteredlist(filterlist)
            }

        }
    }

    private fun getdata() {
        // getting data from firebase
        constants.database.child("subcategory").child(type).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (catsnap in snapshot.children){
                        val cat = subcatmodel()

                        val data: HashMap<String, Any> = catsnap.value as HashMap<String, Any>
                        cat.img = data["img"].toString()
                        cat.price = data["price"].toString()
                        cat.title = data["title"].toString()


                        catdata.add(cat)
                    }
                    adapter = subcatadapter(catdata,this@SubCat)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}