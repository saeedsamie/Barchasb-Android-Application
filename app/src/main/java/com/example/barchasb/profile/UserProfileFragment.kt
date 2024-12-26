package com.example.barchasb.profile

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.auth.AuthViewModel
import com.example.barchasb.auth.LogoutState
import com.example.barchasb.databinding.FragmentUserProfileBinding
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        binding.logoutButton.setOnClickListener {
            performLogout()
        }

        observeLogoutState()

        return binding.root
    }

    private fun observeLogoutState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.logoutState.collect { state ->
                    when (state) {
                        is LogoutState.Idle -> {
                            // No action needed
                        }

                        is LogoutState.Loading -> {
                            // Show loading spinner (if applicable)
                            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
                        }

                        is LogoutState.Success -> {
                            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_userProfileFragment_to_loginFragment)
                            TokenManager.clearToken(requireContext())
                            println("Token cleared")
                        }

                        is LogoutState.Error -> {
                            Toast.makeText(
                                context, "Logout failed: ${state.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun performLogout() {
        val apiToken = TokenManager.getToken(requireContext())
        println("Token saved: $apiToken")
        authViewModel.logout(apiToken.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

