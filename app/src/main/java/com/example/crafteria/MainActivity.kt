package com.example.crafteria
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.crafteria.databinding.ActivityMainBinding
import com.example.crafteria.helpers.constants
import com.example.crafteria.homefragment.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var parentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        parentLayout = findViewById(android.R.id.content)

        replacefragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replacefragment(HomeFragment())
                R.id.cat -> replacefragment(CatFragment())
                R.id.cart -> replacefragment(CartFragment())
                R.id.account -> replacefragment(AccountFragment())
                R.id.address -> replacefragment(AddressFragment())

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