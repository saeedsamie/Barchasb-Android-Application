package com.example.barchasb.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)

        val users = listOf(
            LeaderboardItem(getString(R.string.username_1), 100),
            LeaderboardItem(getString(R.string.username_2), 90),
            LeaderboardItem(getString(R.string.username_3), 80),
            LeaderboardItem(getString(R.string.username_4), 50)
        )

        val leaderboardAdapter = LeaderboardAdapter(users)
        binding.leaderboardRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.leaderboardRecyclerView.adapter = leaderboardAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

