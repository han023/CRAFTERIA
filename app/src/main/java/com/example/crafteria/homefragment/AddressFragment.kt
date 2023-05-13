package com.example.crafteria.homefragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crafteria.MainActivity
import com.example.crafteria.R
import com.example.crafteria.databinding.FragmentAddressBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class AddressFragment : Fragment() {

    private lateinit var binding:FragmentAddressBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var parentLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddressBinding.inflate(layoutInflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = requireActivity().findViewById(android.R.id.content)

        var address = ""
        constants.database.child("user")
            .child(sharedPreferences.getString("mobile", "").toString()).child("address")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        address = snapshot.value.toString()
                        val list = address.split(",@#", ".")

                        binding.line1.setText(list[0])
                        binding.line2.setText(list[1])
                        binding.postal.setText(list[2])
                        binding.city.setText(list[3])
                    }else{
                        address = ""
                        binding.line1.setText("")
                        binding.line2.setText("")
                        binding.postal.setText("")
                        binding.city.setText("")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        binding.dell.setOnClickListener {
            constants.database.child("user")
                .child(sharedPreferences.getString("mobile", "").toString()).child("address")
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(parentLayout, "deleted", Snackbar.LENGTH_SHORT).show()

                    } else {
                        Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
        }

        binding.registarasseller.setOnClickListener {
            val line1 = binding.line1.text.toString().trim()
            val line2 = binding.line2.text.toString().trim()
            val postal = binding.postal.text.toString().trim()
            val city = binding.city.text.toString().trim()

            if (line1.isEmpty() || line2.isEmpty() || postal.isEmpty() || city.isEmpty()){
                Snackbar.make(parentLayout, "fill all fields", Snackbar.LENGTH_SHORT).show()
            } else if(postal.length != 5){
                Snackbar.make(parentLayout, "check postal code", Snackbar.LENGTH_SHORT).show()
            } else {

                constants.database.child("user")
                    .child(sharedPreferences.getString("mobile", "").toString()).child("address")
                    .setValue(line1 + ",@#" + line2 + ",@#" + postal + ",@#" + city)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {


                            Snackbar.make(parentLayout, "updated", Snackbar.LENGTH_SHORT).show()

                        } else {
                            Snackbar.make(parentLayout, "Try Again Later", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
            }


        }


        return binding.root
    }


}