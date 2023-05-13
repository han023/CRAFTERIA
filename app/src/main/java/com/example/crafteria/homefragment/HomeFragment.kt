package com.example.crafteria.homefragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crafteria.R
import com.example.crafteria.databinding.FragmentHomeBinding
import com.example.crafteria.helpers.constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener



class HomeFragment : Fragment() {
    // variables of HomeFragment class

    private lateinit var binding: FragmentHomeBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var parentLayout: View

    // function to create view of fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = requireActivity().findViewById(android.R.id.content)

        // action to take if user clicks on profile option
        binding.profile.setOnClickListener {
            replacefragment(AccountFragment())
        }
        // action to take if user clicks on cart option
        binding.cart.setOnClickListener {
            replacefragment(CartFragment())
        }
        // no actions for delivery and payment
        binding.delivery.setOnClickListener {
            replacefragment(CartFragment())
        }
        binding.payment.setOnClickListener {
            replacefragment(AddressFragment())
        }

        return binding.root
    }

    private fun replacefragment(fragment:Fragment){
        // this function is actually used to replace frame layout with fragment using fragment manager

        val fragmentmanger = requireActivity().supportFragmentManager
        val fragmenttransaction = fragmentmanger.beginTransaction()
        fragmenttransaction.replace(R.id.framlayout,fragment)
        fragmenttransaction.commit()

    }

}

