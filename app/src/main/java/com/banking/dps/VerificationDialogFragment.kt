package com.banking.dps

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerificationDialogFragment(private val email: String, private val success: Boolean): DialogFragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.verification_dialog_layout, null)

        val verificationInfoTextView = dialogView.findViewById<TextView>(R.id.verificationInfoTextView)

        // check if verification mail was successfully sent
        val verificationInfo = if(success) {
            getString(R.string.verification_info, email)
        } else { getString(R.string.verification_error) }

        verificationInfoTextView.text = verificationInfo

        val resendVerificationEmailButton = dialogView.findViewById<Button>(R.id.resendVerificationEmailButton)
        resendVerificationEmailButton.setOnClickListener { resendVerificationEmail() }

        builder.setView(dialogView)

        auth = Firebase.auth
        return builder.create()
    }

    private fun resendVerificationEmail() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Verification Email Sent Successfully!",
                        Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(
                        context,
                        "Verification Email Not Sent! Please try again later.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}