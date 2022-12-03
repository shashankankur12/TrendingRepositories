package com.example.trendingrepositories.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trendingrepositories.R
import com.example.trendingrepositories.data.pojo.RepoDetails
import kotlinx.android.synthetic.main.item_repo.view.*
import java.util.*


class TrendingRepoAdaptor() : RecyclerView.Adapter<TrendingRepoAdaptor.ViewHolder>() {

    private var searchEnabled = false
    private var searchTerm = ""

    private var repoList: ArrayList<RepoDetails> = ArrayList()
    private var filteredRepoList: ArrayList<RepoDetails> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return if (searchEnabled) filteredRepoList.size else repoList.size
    }

    private fun getItem(position: Int): RepoDetails {
        return if (searchEnabled) filteredRepoList[position] else repoList[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View, private val context: Context) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        fun bind(repoData: RepoDetails) {
            itemView.textRepoName.text = repoData.name
            itemView.textAuthorName.text =
                String.format(context.getString(R.string.author), repoData.author)
            itemView.textStars.text = repoData.stars.toString()
            itemView.textLanguage.text = repoData.language ?: "N/A"
            (itemView.viewLanguageColor.background as GradientDrawable).setColor(
                Color.parseColor(
                    repoData.languageColor ?: "#D8D7D7"
                )
            )

            Glide.with(itemView.context)
                .load(repoData.avatar)
                .apply(
                    RequestOptions().placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .into(itemView.userImage)

            if (repoData.isSelected) {
                itemView.mainItem.setBackgroundResource(R.color.grey)
            } else {
                itemView.mainItem.setBackgroundResource(R.color.white)
            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            view?.let {
                when (view.id) {
                    itemView.id -> {
                        val repoData = getItem(adapterPosition)
                        repoData.isSelected = !repoData.isSelected
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun setSearchEnabled(enabled: Boolean, searchTerm: String = "") {
        this.searchEnabled = enabled
        if (!searchEnabled) {
            this.searchTerm = ""
            filteredRepoList.clear()
            notifyDataSetChanged()
            return
        }
        this.searchTerm = searchTerm.toLowerCase(Locale.getDefault())
        filter()
    }

    private fun filter() {
        filteredRepoList.clear()
        if (searchTerm.isEmpty()) {
            filteredRepoList.addAll(repoList)
        } else {
            for (data in repoList) {
                val term = data.name ?: ""
                if (term.toLowerCase(Locale.getDefault()).contains(searchTerm)) {
                    filteredRepoList.add(data)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun updateRepoData(
        trendingRepoList: List<RepoDetails>,
    ) {
        this.repoList.clear()
        this.repoList.addAll(trendingRepoList)
        notifyDataSetChanged()
    }

}