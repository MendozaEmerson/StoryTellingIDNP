package com.quantumsoft.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumsoft.myapplication.repository.PinturaRepository
import com.quantumsoft.myapplication.repository.SalaRepository

class AdapterRecyclerView(
    private var items: List<Item>,
    private val onImageClickListener: OnImageClickListener) :
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() {

    data class Item(
        val imageResId: Int,
        val title: String,
        val location: String,
        val author: String
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        // Para ejemplo, se establece una imagen fija
        holder.imageView.setImageResource(R.drawable.carpintero_de_nidos)
        holder.titleTextView.text = item.title
        holder.locationTextView.text = item.location
        holder.authorTextView.text = item.author

        holder.imageView.setOnClickListener {
            onImageClickListener.onImageClick(item.imageResId)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val locationTextView: TextView = itemView.findViewById(R.id.item_location)
        val authorTextView: TextView = itemView.findViewById(R.id.item_author)
    }

    // Define la interfaz para el listener de clic en la imagen
    fun interface OnImageClickListener {
        fun onImageClick(imageId: Int)
    }
}
