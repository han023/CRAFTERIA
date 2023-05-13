package com.example.crafteria.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crafteria.FullItemView
import com.example.crafteria.R
import com.example.crafteria.models.subcatmodel


class subcatadapter(private var items: ArrayList<subcatmodel>,private var context: Context) : RecyclerView.Adapter<subcatadapter.ListItemViewHolder>(){

    fun setfilteredlist(items: ArrayList<subcatmodel>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categoryrecyclerview, parent, false)
        return ListItemViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentItem = items[position]

        // downloading image from url that is store in the firebase data using glide
        Glide
            .with(holder.itemView)
            .load(currentItem.img)
            .centerCrop()
            .placeholder(R.drawable.cart)
            .into(holder.image);

        holder.text.text = "Add to cart"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullItemView::class.java)
            intent.putExtra("data", currentItem)
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
