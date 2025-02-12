package com.example.barchasb.leaderboard

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val viewModel: LeaderboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        loadLeaderboard()
    }

    private fun setupRecyclerView() {
        leaderboardAdapter = LeaderboardAdapter()
        binding.leaderboardRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = leaderboardAdapter
        }
    }

    private fun setupObservers() {
        viewModel.leaderboardEntries.observe(viewLifecycleOwner) { entries ->
            leaderboardAdapter.updateData(entries)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let { 
                showError(it)
                // If error is due to authentication, navigate to login
                if (it.contains("authentication", ignoreCase = true)) {
                    findNavController().navigate(R.id.action_leaderboardFragment_to_loginFragment)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun loadLeaderboard() {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            viewModel.fetchLeaderboard(token)
        } else {
            // If no token is found, navigate to login
            findNavController().navigate(R.id.action_leaderboardFragment_to_loginFragment)
            showError("Please log in to view the leaderboard")
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

