package com.example.newsrecap.ui.fragments.news_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsrecap.R
import com.example.newsrecap.databinding.ListItemNewsBinding
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.utils.parseDate

class NewsListAdapter(private val onNewsClicked: (News) -> Unit)
    : ListAdapter<News, NewsListAdapter.NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ListItemNewsBinding.inflate(layoutInflater, parent, false), onNewsClicked)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class NewsViewHolder(private val binding: ListItemNewsBinding,
                         private val onNewsClicked: (News) -> Unit) : RecyclerView.ViewHolder(binding.root) {
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
                tvNewsTime.text = news.publishedAt?.parseDate()

                mcvNewsItem.setOnClickListener {
                    onNewsClicked.invoke(news)
                }
            }
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