package com.example.alle.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alle.R
import com.google.mlkit.vision.label.ImageLabel

class CreateListAdapter(
    private val itemClickListener: ((Uri) -> Unit)? = null,
    private val context: Context,
    private val imageList: List<ImageLabel>
) : RecyclerView.Adapter<CreateListAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_add_notes, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageList[position]
        holder.title.text = imageUrl.text

        holder.add.setImageResource(R.drawable.add)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.collectionItem)
        val add: AppCompatImageView = itemView.findViewById(R.id.add)
    }
}
