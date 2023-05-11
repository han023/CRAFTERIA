package com.example.crafteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.crafteria.databinding.ActivityMainBinding
import com.example.crafteria.homefragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replacefragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replacefragment(HomeFragment())
                R.id.cat -> replacefragment(CatFragment())
                R.id.cart -> replacefragment(CartFragment())
                R.id.account -> replacefragment(AccountFragment())

                else ->{}

            }

            return@setOnItemSelectedListener true
        }

    }

    private fun replacefragment(fragment:Fragment){

        val fragmentmanger = supportFragmentManager
        val fragmenttransaction = fragmentmanger.beginTransaction()
        fragmenttransaction.replace(R.id.framlayout,fragment)
        fragmenttransaction.commit()

    }


}