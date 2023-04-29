package com.banking.dps

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupPage : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signupButton: Button
    private lateinit var goToLoginButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup_page, container, false)

        auth = Firebase.auth
        usernameInput = view.findViewById(R.id.usernameEditText)
        passwordInput = view.findViewById(R.id.passwordEditText)
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordEditText)
        signupButton = view.findViewById(R.id.signupButton)
        signupButton.setOnClickListener{ initiateSignup() }
        goToLoginButton = view.findViewById(R.id.loginTextView)
        goToLoginButton.setOnClickListener{ goToLoginPage() }

        return view
    }

    private fun initiateSignup() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        when {
            username.isEmpty() -> {
                Toast.makeText(context, "Please enter a valid username", Toast.LENGTH_SHORT).show()
            }
            username.length < 2 -> {
                Toast.makeText(context, "Username must be at least 2 characters long", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
            password.length < 8 -> {
                Toast.makeText(context, "Password must contain at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            !containsAlphabetAndDigit(password) -> {
                Toast.makeText(context, "Password must contain at least one alphabet and one digit", Toast.LENGTH_SHORT).show()
            }
            confirmPassword != password -> {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            else -> {
                signupUser(username, password)
            }
        }
    }

    private fun containsAlphabetAndDigit(input: String): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d).+\$".toRegex()
        return regex.matches(input)
    }

    private fun signupUser(username: String, password: String) {
        val sharedPreferences = requireContext().getSharedPreferences(
            LOGIN_DETAILS_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        sharedPreferences.edit()
            .putString(LOGIN_DETAILS, "$username::$password")
            .apply()

        Toast.makeText(context, "User account has been successfully created!\nLogin to begin using the app", Toast.LENGTH_LONG).show()
        goToLoginPage()
    }

    private fun goToLoginPage() {
        findNavController().popBackStack()
    }
}