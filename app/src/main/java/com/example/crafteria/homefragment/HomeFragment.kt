package com.example.crafteria.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crafteria.R
import com.example.crafteria.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.profile.setOnClickListener {
            replacefragment(AccountFragment())
        }
        binding.cart.setOnClickListener {
            replacefragment(CartFragment())
        }
        binding.delivery.setOnClickListener {  }
        binding.payment.setOnClickListener {  }

        return binding.root
    }

    private fun replacefragment(fragment:Fragment){

        val fragmentmanger = requireActivity().supportFragmentManager
        val fragmenttransaction = fragmentmanger.beginTransaction()
        fragmenttransaction.replace(R.id.framlayout,fragment)
        fragmenttransaction.commit()

    }

}

