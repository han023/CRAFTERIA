package com.example.crafteria.homefragment
// Below are library files that I used in my code
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crafteria.R
import com.example.crafteria.adapters.cartadapter
import com.example.crafteria.adapters.categoryadapter
import com.example.crafteria.databinding.FragmentCartBinding
import com.example.crafteria.databinding.FragmentCatBinding
import com.example.crafteria.databinding.FragmentHomeBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.categorymodel
import com.example.crafteria.models.subcatmodel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var catdata : ArrayList<subcatmodel>
    private lateinit var adapter: cartadapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.searchView.clearFocus()

        // Set an OnQueryTextListener for the SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)
                return true
            }
        })




        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)


        catdata = arrayListOf<subcatmodel>()
        getdata()


        return binding.root
    }

    // Function to filter the list based on search text
    private fun filterlist(newText: String?) {
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
    // Function to retrieve data from Firebase Realtime Database
    private fun getdata() {
        constants.database.child("cart").child(sharedPreferences.getString("mobile","").toString())
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded && snapshot.exists()){
                    catdata.clear()
                    var to = 0
                    val key = ArrayList<String>()
                    for (catsnap in snapshot.children){
                        key.add(catsnap.key.toString());
                        val cat = catsnap.getValue(subcatmodel::class.java)
                        catdata.add(cat!!)
                        to += cat.price.toInt()
                    }
                    this@CartFragment.adapter = cartadapter(catdata,this@CartFragment.requireContext(),key)
                    binding.recyclerView.adapter = adapter
                    binding.total.setText("Total : $to")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}