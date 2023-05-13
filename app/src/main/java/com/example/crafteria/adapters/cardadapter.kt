package com.example.crafteria.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crafteria.FinalChecking
import com.example.crafteria.Newcard
import com.example.crafteria.R
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.cardmodel
import com.example.crafteria.models.subcatmodel
import com.example.crafteria.updatecard


class cardadapter(private var items: ArrayList<cardmodel>, private var context: Context,private var data:subcatmodel,
                    private var key:ArrayList<String>)
    : RecyclerView.Adapter<cardadapter.ListItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardrecyclerview, parent, false)
        return ListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentItem = items[position]

        holder.exp.text = currentItem.exp
        holder.name.text = currentItem.name
        holder.cardnumber.text = currentItem.cardnumber

        holder.del.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            constants.database.child("card").child(sharedPreferences.getString("mobile","").toString())
                .child(key[position]).removeValue().addOnCompleteListener {
                    items.clear()
                    key.clear()
                    notifyDataSetChanged()
                }
        }

        holder.up.setOnClickListener {
            val intent = Intent(context, updatecard::class.java)
            intent.putExtra("data", data)
            intent.putExtra("card",currentItem)
            intent.putExtra("key",key[position])
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FinalChecking::class.java)
            intent.putExtra("data", data)
            intent.putExtra("card",currentItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exp: TextView = itemView.findViewById(R.id.exp)
        val cardnumber: TextView = itemView.findViewById(R.id.cardnumber)
        val name: TextView = itemView.findViewById(R.id.name)
        val del:ImageView = itemView.findViewById(R.id.del)
        val up:ImageView = itemView.findViewById(R.id.up)
    }


}