package com.sarath.dev.covid.controllers.adapters

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.R
import com.sarath.dev.covid.controllers.network.news.NewsArticlesResponse
import com.squareup.picasso.Picasso

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    companion object {
        var items: ArrayList<NewsArticlesResponse>? = ArrayList()

        fun getItem(position: Int): NewsArticlesResponse {
            return items!![position]
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var parentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout)
        var logoImageView: ImageView = itemView.findViewById(R.id.logo_image_view)
        var titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        var descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        var contentTextView: TextView = itemView.findViewById(R.id.content_text_view)

        init {
            parentLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v == parentLayout) {
                if (!getItem(position = adapterPosition).url.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getItem(position = adapterPosition).url)
                    COVID19.context!!.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(COVID19.context!!).inflate(R.layout.list_item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (items == null) {
            0
        } else {
            items!!.size
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items!![position]

        if (item.title != null) holder.titleTextView.text = item.title
        if (item.description != null) holder.descriptionTextView.text = item.description
        if (item.content != null) holder.contentTextView.text = item.content

        if (item.urlToImage != null) {
            Picasso.with(COVID19.context!!).load(item.urlToImage).into(holder.logoImageView)
            holder.logoImageView.visibility = View.VISIBLE
        } else {
            holder.logoImageView.visibility = View.GONE
        }
    }
}