package com.example.crafteria.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crafteria.R
import com.example.crafteria.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    // variables of HomeFragment class

    private lateinit var binding: FragmentHomeBinding

    // function to create view of fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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
            replacefragment(AddressFragment())
        }
        binding.payment.setOnClickListener {
            replacefragment(CartFragment())
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

