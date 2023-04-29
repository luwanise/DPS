package com.banking.dps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordPage : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var signupTextView: TextView
    private lateinit var navController: NavController
    private lateinit var dialog: AlertDialog
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password_page, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)
        resetPasswordButton = view.findViewById(R.id.resetPasswordButton)
        resetPasswordButton.setOnClickListener { sendResetEmail() }
        signupTextView = view.findViewById(R.id.signUpTextView)
        signupTextView.setOnClickListener { openSignupPage() }

        navController = findNavController()
        auth = Firebase.auth
        return view
    }

    private fun sendResetEmail() {
        showLoadingPopup()
        val email = emailEditText.text.toString().trim()
        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password reset email sent successfully
                        Toast.makeText(requireContext(), "Password reset email sent", Toast.LENGTH_LONG).show()
                    } else {
                        // Failed to send password reset email
                        Toast.makeText(requireContext(), "Failed to send password reset email", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            emailEditText.error = "Please enter your email"
        }
        dialog.dismiss()
    }

    private fun showLoadingPopup() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_popup, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun openSignupPage() {
        navController.navigate(
            R.id.action_forgotPasswordPage_to_signupPage,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.forgotPasswordPage, true)
                .build()
        )
    }
}