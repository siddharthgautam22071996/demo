package com.example.alle.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alle.R
import com.google.mlkit.vision.label.ImageLabel

class CollectionAdapter(private val context: Context, private val collectList: List<ImageLabel>) :
    RecyclerView.Adapter<CollectionAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_info_collection, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.title.text = collectList[position].text
    }

    override fun getItemCount(): Int {
        return collectList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.collectionItem)
    }
}
