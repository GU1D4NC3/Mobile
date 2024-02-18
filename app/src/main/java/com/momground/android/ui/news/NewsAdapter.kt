package com.momground.android.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.momground.android.R
import com.momground.android.data.NewsItem


class NewsAdapter(val context: Context, val items: List<NewsItem>):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_newsletter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val category = view.findViewById<TextView>(R.id.category)
        val cover = view.findViewById<ImageView>(R.id.cover)

        fun bind(item: NewsItem) {
            title.text = item.title
            category.text = item.category
            if(item.cover_url.isNullOrEmpty()) item.cover_url = "https://img.freepik.com/premium-vector/corporate-business-newsletter-design_71956-249.jpg"
            Glide.with(context)
                .load(item.cover_url)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
                .placeholder(R.drawable.img_cover_sample)
                .fallback(R.drawable.img_cover_sample) //fallback은 아님
                .into(cover)

        }
    }
}