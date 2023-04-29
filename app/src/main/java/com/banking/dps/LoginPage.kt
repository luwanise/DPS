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

class LoginPage : Fragment() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var goToSignupButton: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login_page, container, false)

        usernameInput = view.findViewById(R.id.usernameEditText)
        passwordInput = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener { initiateLogin() }
        goToSignupButton = view.findViewById(R.id.signUpTextView)
        goToSignupButton.setOnClickListener { goToSignupPage() }
        auth = Firebase.auth

        return view
    }

    private fun initiateLogin(){
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()

        when {
            username.isEmpty() -> {
                Toast.makeText(context, "Please enter your email address", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
            password.length < 8 -> {
                Toast.makeText(context, "Password must contain at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            !containsAlphabetAndDigit(password) -> {
                Toast.makeText(context, "Password must contain both alphabets and digits", Toast.LENGTH_SHORT).show()
            }
            else -> {
                loginUser(username, password)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        val sharedPreferences = requireContext().getSharedPreferences(LOGIN_DETAILS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val loginDetails = sharedPreferences.getString(LOGIN_DETAILS, null)
        if (loginDetails != null){
            val loginDetailsList = loginDetails.split("::")
            val savedUsername = loginDetailsList[0]
            val savedPassword = loginDetailsList[1]

            if (username == savedUsername){
                if (password == savedPassword){
                    Toast.makeText(context, "User Verification Complete! Welcome $username", Toast.LENGTH_LONG).show()
                    goToHomePage()
                } else {
                    Toast.makeText(context, "The password you entered is not correct! Please enter a valid password.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Invalid Username! Please enter a valid username.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "This device has not been registered! Please sign up instead.", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToHomePage() {
        findNavController().navigate(R.id.action_loginPage_to_mainTabsPage)
    }

    private fun containsAlphabetAndDigit(input: String): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d).+\$".toRegex()
        return regex.matches(input)
    }

    private fun goToSignupPage() {
        findNavController().navigate(R.id.action_loginPage_to_signupPage)
    }
}