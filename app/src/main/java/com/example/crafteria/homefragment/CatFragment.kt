package com.example.crafteria.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crafteria.adapters.categoryadapter
import com.example.crafteria.databinding.FragmentCatBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.categorymodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*


class CatFragment : Fragment() {

/*binding is an instance of the FragmentCatBinding class, which is responsible for binding the views in the fragment's layout.
catdata is an ArrayList of categorymodel objects, which will store the category data.
adapter is an instance of the categoryadapter class, which will be used to populate and manage the items in the RecyclerView. */

    private lateinit var binding: FragmentCatBinding
    private lateinit var catdata : ArrayList<categorymodel>
    private lateinit var adapter:categoryadapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment using the FragmentCatBinding.
        binding = FragmentCatBinding.inflate(inflater, container, false)

        // Clear the focus from the search view to prevent it from being selected by default.
        binding.searchView.clearFocus()

        // Set a listener for the search view's text input events using an anonymous implementation of SearchView.OnQueryTextListener.
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
                // Return false to indicate that the query text submission is not handled here.
            }
            // Call the filterlist function to perform filtering based on the entered text.
            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)
                return true
                // Return true to indicate that the query text change is handled here.
            }
        })

        // Using GridLayoutManager class
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.setHasFixedSize(true)

        // Create an ArrayList to hold the category data.
        catdata = arrayListOf<categorymodel>()

        // Call the getdata function to fetch the category data.
        getdata()


        return binding.root
    }

    /**
     * Filters the category list based on the entered text.
     *
     * @param newText The text entered in the search view.
     */
    private fun filterlist(newText: String?) {
        if(newText != null){
            val filterlist = ArrayList<categorymodel>()

            // Iterate through the catdata list and check if the title contains the entered text.
            for(i in catdata){
                if (i.title.lowercase(Locale.ROOT).contains(newText)){
                    filterlist.add(i)
                }
            }

            // If the filtered list is not empty, update the adapter with the filtered data.
            if (filterlist.isNotEmpty()){
                adapter.setfilteredlist(filterlist)
            }
        }
    }

    /**
     * Retrieves category data from the Firebase Realtime Database and populates the adapter.
     */
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
                // Handle the cancellation of data retrieval if needed.
                // Currently, the code block is left empty.
            }
        })
    }


}