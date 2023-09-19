package com.example.newsrecap.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsrecap.R
import com.example.newsrecap.databinding.ListItemNewsBinding
import com.example.newsrecap.domain.model.News
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter: ListAdapter<News, NewsListAdapter.NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ListItemNewsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class NewsViewHolder(private val binding: ListItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.apply {
                news.urlToImage?.let {
                    Glide.with(itemView)
                        .load(it)
                        .placeholder(R.drawable.loading_animation_24)
                        .error(R.drawable.vector_broken_image)
                        .into(ivNewsImage)
                }

                tvNewsTitle.text = news.title
                tvNewsDescription.text = news.description
                tvMediaTitle.text = news.source?.name

                news.publishedAt?.let {
                    try {
                        parseTime(it, "yyyy-MM-dd'T'HH:mm:ss'Z'", tvNewsTime)
                    } catch (e: Exception) {
                        parseTime(it, "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", tvNewsTime)
                    }
                }
            }
        }

        private fun parseTime(publishedAt: String, format: String, view: TextView) {
            val inputFormat = SimpleDateFormat(format, Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm | dd.MM", Locale.getDefault())
            val date = inputFormat.parse(publishedAt)
            val output = date?.let { outputFormat.format(it) }
            view.text = output
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }
}