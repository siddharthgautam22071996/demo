package com.example.alle.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.example.alle.R
import com.example.alle.ui.viewHolder.NormalItemViewHolder
import com.google.mlkit.vision.label.ImageLabel

class CollectionBottomSheetAdapter(
    private val context: Context,
    private val collectList: List<ImageLabel>,
    private val onEditButtonClick: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_NORMAL_ITEM -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_add_notes, parent, false)
                NormalItemViewHolder(view)
            }

            VIEW_TYPE_INPUT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_inputbox, parent, false)
                EditButtonViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NormalItemViewHolder -> {
                holder.bind(collectList[position])
            }

            is EditButtonViewHolder -> {
                holder.bind(onEditButtonClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return collectList.size +1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            VIEW_TYPE_INPUT
        } else {
            VIEW_TYPE_NORMAL_ITEM
        }
    }

    class EditButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val editButton: AppCompatEditText = itemView.findViewById(R.id.input)

        fun bind(onEditButtonClick: () -> Unit) {
//            editButton.setOnClickListener {
//                onEditButtonClick.invoke()
//            }
        }
    }

    companion object {
        private const val VIEW_TYPE_NORMAL_ITEM = 1
        private const val VIEW_TYPE_INPUT = 2
    }
}
