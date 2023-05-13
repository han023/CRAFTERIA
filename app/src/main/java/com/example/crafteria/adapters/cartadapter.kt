package com.example.crafteria.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crafteria.Cards
import com.example.crafteria.R
import com.example.crafteria.helpers.constants
import com.example.crafteria.models.subcatmodel

class cartadapter(private var items: ArrayList<subcatmodel>, private var context: Context,private var key: ArrayList<String>)
    : RecyclerView.Adapter<cartadapter.ListItemViewHolder>(){

    fun setfilteredlist(items: ArrayList<subcatmodel>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cartrecyclerview, parent, false)
        return ListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentItem = items[position]

        // downloading image from url that is store in the firebase data using glide
        Glide
            .with(holder.itemView)
            .load(currentItem.img)
            .centerCrop()
            .placeholder(R.drawable.cart)
            .into(holder.image);

        holder.text.text = currentItem.title
        holder.price.text = currentItem.price

        holder.del.setOnClickListener{
            if (position != RecyclerView.NO_POSITION) {
                val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                constants.database.child("cart").child(sharedPreferences.getString("mobile","").toString())
                    .child(key[position]).removeValue().addOnCompleteListener {
                        items.clear()
                        key.clear()
                        notifyDataSetChanged()
                    }
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Cards::class.java)
            intent.putExtra("data",currentItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // item view for recycler view
        val image: ImageView = itemView.findViewById(R.id.img)
        val text: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val del: ImageView = itemView.findViewById(R.id.del)
    }


}
