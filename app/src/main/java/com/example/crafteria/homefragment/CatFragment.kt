package com.example.crafteria.homefragment

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crafteria.adapters.categoryadapter
import com.example.crafteria.databinding.FragmentCatBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.categorymodel
import com.example.crafteria.subcat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


class CatFragment : Fragment() {

    private lateinit var binding: FragmentCatBinding
    private lateinit var catdata : ArrayList<categorymodel>
    private lateinit var adapter:categoryadapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)

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




        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.setHasFixedSize(true)


        catdata = arrayListOf<categorymodel>()
        getdata()




        return binding.root
    }

    private fun filterlist(newText: String?) {
        if(newText != null){
            val filterlist = ArrayList<categorymodel>()
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
        constants.database.child("category").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (catsnap in snapshot.children){
                        val cat = catsnap.getValue(categorymodel::class.java)
                        catdata.add(cat!!)
                    }
                    adapter = categoryadapter(catdata,requireContext())
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}