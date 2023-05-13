package com.example.crafteria.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crafteria.R
import com.example.crafteria.SubCat
import com.example.crafteria.models.categorymodel
import java.util.*


class categoryadapter(private var items: ArrayList<categorymodel>,private var context:Context) : RecyclerView.Adapter<categoryadapter.ListItemViewHolder>(){

    fun setfilteredlist(items: ArrayList<categorymodel>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categoryrecyclerview, parent, false)
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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubCat::class.java)
            intent.putExtra("cat",currentItem.title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // item view for recycler view
        val image: ImageView = itemView.findViewById(R.id.catimg)
        val text: TextView = itemView.findViewById(R.id.cartext)



    }


}
