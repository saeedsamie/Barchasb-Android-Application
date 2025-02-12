package com.example.barchasb.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barchasb.api.LeaderboardEntry
import com.example.barchasb.databinding.ItemLeaderboardBinding

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    private var leaderboardItems: List<LeaderboardEntry> = emptyList()

    inner class LeaderboardViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LeaderboardEntry) {
            binding.usernameEditText.text = item.name
            binding.points.text = "${item.points}"
        }
    }

    fun updateData(newItems: List<LeaderboardEntry>) {
        leaderboardItems = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding =
            ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(leaderboardItems[position])
    }

    override fun getItemCount(): Int = leaderboardItems.size
}
