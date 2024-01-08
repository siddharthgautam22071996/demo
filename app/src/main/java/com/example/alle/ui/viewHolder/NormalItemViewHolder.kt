package com.example.alle.ui.viewHolder


import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alle.R
import com.google.mlkit.vision.label.ImageLabel

class NormalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: AppCompatTextView = itemView.findViewById(R.id.collectionItem)
    private val add: AppCompatImageView = itemView.findViewById(R.id.add)

    fun bind(item: ImageLabel) {
        title.text = item.text
        add.setImageResource(R.drawable.close)
    }

}