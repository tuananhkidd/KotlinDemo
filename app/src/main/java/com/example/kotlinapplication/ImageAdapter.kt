package com.example.kotlinapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageAdapter(val context: Context, var uris: ArrayList<Image?>, var clickAddButton: OnClickAddItem) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_VIEW_ADD = 0
        const val ITEM_VIEW_IMAGE = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VIEW_IMAGE) {
            return ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false))
        }
        return AddViewHolder(LayoutInflater.from(context).inflate(R.layout.item_add, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (uris.get(position) == null && position == uris.size - 1) {
            return ITEM_VIEW_ADD
        }
        return ITEM_VIEW_IMAGE
    }

    override fun getItemCount(): Int {
        return uris.size
    }

    override fun onBindViewHolder(viewholder: RecyclerView.ViewHolder, p1: Int) {
        when (getItemViewType(p1)) {
            ITEM_VIEW_IMAGE -> {
                val imageViewHolder: ImageViewHolder = viewholder as ImageViewHolder
                Glide.with(context)
                    .load(uris.get(p1)?.uri)
                    .into(imageViewHolder.iv)
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.iv)
        val iv_cancle: ImageView = itemView.findViewById(R.id.iv_cancle)

        init {
            iv_cancle.setOnClickListener {
                clickAddButton.onCancleImage(adapterPosition)
            }
        }
    }

    inner class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.iv_add)

        init {
            iv.setOnClickListener {
                clickAddButton.onClickAddButton()
            }
        }
    }


    interface OnClickAddItem {
        fun onClickAddButton()
        fun onCancleImage(position: Int)
    }
}