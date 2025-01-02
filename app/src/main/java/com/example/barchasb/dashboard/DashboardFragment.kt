package com.example.barchasb.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentDashboardBinding
import com.example.barchasb.profile.UserViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ایجاد یک Callback برای مدیریت دکمه بازگشت
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        // اضافه کردن Callback به سیستم بازگشت
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = TokenManager.getToken(requireContext()) ?: return
        userViewModel.fetchUserProfile(token)

        userViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            userProfile?.let {
                binding.usernameTextView.text = it.username
                binding.userPointsTextView.text = it.points.toString()

                // Use Glide or similar library to load avatar
//                Glide.with(this).load(it.avatarUrl).into(binding.avatarImageView)
            }
        }
        // ناوبری به صفحه Task List
        binding.viewNewTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_taskListFragment)
        }
        binding.viewAllDoneTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_taskListFragment)
        }
        binding.viewTodayDoneTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_taskListFragment)
        }

        // ناوبری به صفحه Leaderboard
        binding.viewLeaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_leaderboardFragment)
        }

        binding.ViewUserProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_userProfileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
