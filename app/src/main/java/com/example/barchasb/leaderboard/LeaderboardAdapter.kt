package com.example.barchasb.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barchasb.databinding.ItemLeaderboardBinding

data class LeaderboardItem(val username: String, val score: String)

class LeaderboardAdapter(private val leaderboardItems: List<LeaderboardItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LeaderboardItem) {
            binding.usernameEditText.text = item.username
            binding.score.text = item.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(leaderboardItems[position])
    }

    override fun getItemCount(): Int = leaderboardItems.size
}
