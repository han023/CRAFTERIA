package com.example.crafteria.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crafteria.R
import com.example.crafteria.databinding.FragmentCatBinding
import com.example.crafteria.databinding.FragmentHomeBinding


class CatFragment : Fragment() {

    private lateinit var binding: FragmentCatBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatBinding.inflate(inflater, container, false)



        return binding.root
    }

}