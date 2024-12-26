package com.example.barchasb.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.AuthApi
import com.example.barchasb.api.User
import com.example.barchasb.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Create an instance of the Auth API
    private val authApi: AuthApi by lazy { ApiClient.create<AuthApi>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle back button behavior
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Optional: Disable back navigation or handle custom behavior
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to SignUpFragment when sign-up text is clicked
        binding.signUpRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        // Handle login button click
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Username or password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                performLogin(username, password)
            }
        }
    }

    private fun performLogin(username: String, password: String) {
        lifecycleScope.launch {
            try {
                // Call the login API
                val response = authApi.login(User(username, password))
                if (response.isSuccessful) {
                    // Navigate to DashboardFragment on success
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                } else {
                    // Show error message on failure
                    Toast.makeText(
                        requireContext(),
                        "Login failed: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Handle network or other unexpected errors
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
