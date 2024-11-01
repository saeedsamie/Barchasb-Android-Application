package com.example.barchasb.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ناوبری به صفحه Task List
        binding.viewTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_taskListFragment)
        }

        // ناوبری به صفحه Leaderboard
        binding.viewLeaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_leaderboardFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
