package com.example.crafteria.homefragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.crafteria.*
import com.example.crafteria.databinding.FragmentAccountBinding
import com.example.crafteria.databinding.FragmentHomeBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.registarmodel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var parentLayout:View
    lateinit var data: registarmodel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = requireActivity().findViewById<View>(android.R.id.content);


        try {
            if ( sharedPreferences.contains("mobile") &&
                sharedPreferences.getString("mobile","").toString() != "" ){
            constants.database.child("user").child(sharedPreferences.getString("mobile","").toString())
                .addValueEventListener(object : ValueEventListener {
                    @SuppressLint("SetTextI18n")
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get the user object from the snapshot
                        data = dataSnapshot.getValue(registarmodel::class.java)!!
                        binding.fname.text = "First name: "+ data?.firstname
                        binding.lname.text = "Last name: "+ data?.lastname
                        binding.phone.text = "No: "+ data?.mobile
                        binding.email.text = "Email: "+ data?.email
                        binding.address.text = "Address: "+ data?.address!!.replace(",@#","\n")

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Snackbar.make(parentLayout, "Unable to fetch the data", Snackbar.LENGTH_SHORT).show()
                    }
                })
            }
        } catch (e:Exception){
            Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_SHORT).show()
        }





        binding.update.setOnClickListener{
            val intent = Intent(requireContext(),UpdateProfile::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }

        binding.logout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.remove("mobile")
            editor.apply()

            Snackbar.make(parentLayout, "Sucessfull Logout", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), Loginascustomer::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


        binding.def.setOnClickListener {
            val intent = Intent(requireContext(),updateaddress::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }

        binding.delete.setOnClickListener {
            constants.auth.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        constants.database.child("order")
                            .child( sharedPreferences.getString("mobile","").toString() ).removeValue()


                                 constants.database.child("card").child(sharedPreferences.getString("mobile","").toString()).removeValue()
                                    constants.database.child("cart").child(sharedPreferences.getString("mobile","").toString()).removeValue()
                                    constants.database.child("user").child(sharedPreferences.getString("mobile","").toString()).removeValue()
                                    Snackbar.make(parentLayout, "Deleted sucessfully", Snackbar.LENGTH_SHORT).show()

                        val editor = sharedPreferences.edit()
                        editor.remove("mobile")
                        editor.apply()

                                    val intent = Intent(requireActivity(), Loginascustomer::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)


                    } else {
                        Snackbar.make(parentLayout, "Try again later", Snackbar.LENGTH_SHORT).show()
                    }
                }



        }

        return binding.root
    }


}