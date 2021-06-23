package com.joule.testaeon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.joule.testaeon.R
import com.joule.testaeon.dataClass.Photos


class PhotosAdapter(val items: ArrayList<Photos>) : RecyclerView.Adapter<PhotosAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img)
        val title = itemView.findViewById<TextView>(R.id.tv_title)

        fun onBind(get: Photos) {
            title.text = get.title


            val url = GlideUrl(
                get.thumbnailUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build()
            )
            Glide.with(itemView.context)
                .load(url)
                .centerCrop()
                .into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.item_photos,
            parent,
            false
        )
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.viewHolder, position: Int) {
        holder.onBind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}