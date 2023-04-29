package com.banking.dps

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hover.sdk.api.HoverParameters
import kotlin.random.Random

class SendMoneyPage : Fragment() {

    private lateinit var recipientAccountNumberEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var sendMoneyButton: Button
    private lateinit var dialog: AlertDialog
    private var bankName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_send_money_page, container, false)

        recipientAccountNumberEditText = view.findViewById(R.id.recipientAccountNoEditText)
        amountEditText = view.findViewById(R.id.amountEditText)
        sendMoneyButton = view.findViewById(R.id.sendMoneyButton)
        sendMoneyButton.setOnClickListener { initiateSendMoneyOperation() }

        val selectedBank = arguments?.getString(SELECTED_BANK)

        if (selectedBank != null) bankName = selectedBank

        return view
    }

    private fun initiateSendMoneyOperation() {
        when(bankName){
            "Access Bank" -> showToastMessage()
            "First Bank" -> showToastMessage()
            "Guarantee Trust Bank" -> transferMoney("d9c804c9")
            "Unity Bank" -> showToastMessage()
            "Zenith Bank" -> showToastMessage()
            "First bank dummy bank" -> transferMoney("56c1c66e")
        }
    }

    private fun showToastMessage() {
        Toast.makeText(context, "This feature hasn't been implemented yet!", Toast.LENGTH_SHORT).show()
    }

    private fun transferMoney(requestId: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.password_query_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        val enterPasswordEditText = dialogView.findViewById<EditText>(R.id.enterPasswordEditText)
        val cancelButton = dialogView.findViewById<Button>(R.id.passwordDialogCancelButton)
        val nextButton = dialogView.findViewById<Button>(R.id.passwordDialogNextButton)

        cancelButton.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "Failed to add account!", Toast.LENGTH_LONG).show()
            openHomePage()
        }

        nextButton.setOnClickListener {
            val password = enterPasswordEditText.text.toString()
            try {
                runHoverBuilder(password, requestId)
            } catch(e: Exception){
                Toast.makeText(context, "Error! Transaction Failed!", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Hover Error: $e")
            }
        }

        dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun runHoverBuilder(password: String, requestId: String){
        dialog.dismiss()
        val amount = amountEditText.text.toString()
        val accountNo = recipientAccountNumberEditText.text.toString()

        updateTransactionHistory(amount, accountNo)

        val intent = HoverParameters.Builder(requireContext().applicationContext)
            .request(requestId)
            .extra("amount", amount)
            .extra("accountNumber", accountNo)
            .extra("password", password)
            .buildIntent()

        startActivity(intent)
    }

    private fun updateTransactionHistory(amount: String, accountNo: String) {
        val transactionDetail = "Transferred from $bankName to account number: $accountNo::$amount"
        val transactionId = Random(System.currentTimeMillis()).nextInt(10_000_000, 99_999_999).toString()

        val sharedPreferences = requireContext().getSharedPreferences(HISTORY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(transactionId, transactionDetail)
            .apply()
    }

    private fun openHomePage() {
        parentFragmentManager.popBackStack()
    }
}