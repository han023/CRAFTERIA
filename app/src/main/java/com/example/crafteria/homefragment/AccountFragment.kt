package com.example.crafteria.homefragment

// Below are library files that I used in my code
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
    // These are my class variables
    // late-init mean late initializing
    // Declare the necessary variables and objects

    private lateinit var binding: FragmentAccountBinding // View binding object for the account fragment
    private lateinit var sharedPreferences: SharedPreferences // SharedPreferences object for storing data
    lateinit var parentLayout: View // Parent layout for accessing the root view
    lateinit var data: registarmodel // Data object of type registarmodel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout using the FragmentAccountBinding object, which provides access to the views in the account fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        // Get the SharedPreferences object for storing data, using the "MyPrefs" name and the private mode
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Get the parent layout of the current activity using the android.R.id.content identifier
        parentLayout = requireActivity().findViewById<View>(android.R.id.content)


// Check if the SharedPreferences contains a "mobile" key and the value is not empty
        try {
            if (sharedPreferences.contains("mobile") && sharedPreferences.getString("mobile", "")
                    .toString() != ""
            ) {

                // Retrieve user data from the Firebase Realtime Database based on the "mobile" value
                constants.database.child("user")
                    .child(sharedPreferences.getString("mobile", "").toString())
                    .addValueEventListener(object : ValueEventListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // Get the user object from the snapshot
                            data = dataSnapshot.getValue(registarmodel::class.java)!!

                            // Set the text for the corresponding views in the layout with the retrieved data
                            binding.fname.text = "First name: " + data?.firstname
                            binding.lname.text = "Last name: " + data?.lastname
                            binding.phone.text = "No: " + data?.mobile
                            binding.email.text = "Email: " + data?.email
                            binding.address.text =
                                "Address: " + data?.address!!.replace(",@#", "\n")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Show a Snackbar message indicating that the data fetching was unsuccessful
                            Snackbar.make(
                                parentLayout,
                                "Unable to fetch the data",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        } catch (e: Exception) {
            // Show a Snackbar message with the caught exception if an error occurs
            Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_SHORT).show()
        }


        // Set an onClickListener for the "Update" button
        binding.update.setOnClickListener {
            // Create an intent to navigate to the UpdateProfile activity
            val intent = Intent(requireContext(), UpdateProfile::class.java)
            intent.putExtra("data", data) // Pass the data object as an extra to the intent
            startActivity(intent) // Start the UpdateProfile activity
        }

// Set an onClickListener for the "Logout" button
        binding.logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("mobile") // Remove the "mobile" key from SharedPreferences
            editor.apply()

            // Show a Snackbar message indicating successful logout
            Snackbar.make(parentLayout, "Successful Logout", Snackbar.LENGTH_SHORT).show()

            // Create an intent to navigate to the Loginascustomer activity
            val intent = Intent(requireActivity(), Loginascustomer::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent) // Start the Loginascustomer activity
        }

// Set an onClickListener for the "Default" button
        binding.def.setOnClickListener {
            // Create an intent to navigate to the updateaddress activity
            val intent = Intent(requireContext(), updateaddress::class.java)
            intent.putExtra("data", data) // Pass the data object as an extra to the intent
            startActivity(intent) // Start the updateaddress activity
        }

// Set an onClickListener for the "Delete" button
        binding.delete.setOnClickListener {
            constants.auth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Remove the user-related data from the Firebase Realtime Database
                    constants.database.child("order")
                        .child(sharedPreferences.getString("mobile", "").toString()).removeValue()
                    constants.database.child("card")
                        .child(sharedPreferences.getString("mobile", "").toString()).removeValue()
                    constants.database.child("cart")
                        .child(sharedPreferences.getString("mobile", "").toString()).removeValue()
                    constants.database.child("user")
                        .child(sharedPreferences.getString("mobile", "").toString()).removeValue()

                    // Show a Snackbar message indicating successful deletion
                    Snackbar.make(parentLayout, "Deleted successfully", Snackbar.LENGTH_SHORT)
                        .show()

                    val editor = sharedPreferences.edit()
                    editor.remove("mobile") // Remove the "mobile" key from SharedPreferences
                    editor.apply()

                    // Create an intent to navigate to the Loginascustomer activity
                    val intent = Intent(requireActivity(), Loginascustomer::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent) // Start the Loginascustomer activity
                } else {
                    Snackbar.make(parentLayout, "Try again later", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root // Return the root view of the binding as the result of the function
    }

    }