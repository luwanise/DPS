package com.banking.dps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SelectedBankPage : Fragment() {

    private lateinit var bankName: String
    private lateinit var showBalanceButton: Button
    private lateinit var accountBalanceText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selected_bank_page, container, false)

        val bankNameTextView = view.findViewById<TextView>(R.id.bankName)

        showBalanceButton = view.findViewById(R.id.showBalanceButton)
        accountBalanceText = view.findViewById(R.id.accountBalance)

        val sendMoneyButton = view.findViewById<Button>(R.id.sendMoneyButton)
        val selectedBank = arguments?.getString(SELECTED_BANK)
        val accountId = arguments?.getInt(SELECTED_BANK_ID)

        if (selectedBank != null) {
            bankName = selectedBank
            bankNameTextView.text = bankName
        }

        sendMoneyButton.setOnClickListener { openSendMoneyPage() }

        val deleteAccountButton = view.findViewById<Button>(R.id.deleteAccount)
        deleteAccountButton.setOnClickListener { deleteAccount(accountId) }

        requestCallPermission()
        hideBalance()

        return view
    }

    private fun deleteAccount(bankId: Int?) {
        if (bankId != null){
            val sharedPreferences = requireContext().getSharedPreferences(
                USER_ACCOUNT_SHARED_PREFERENCES, Context.MODE_PRIVATE
            )

            sharedPreferences.edit()
                .remove(bankId.toString())
                .apply()

            Toast.makeText(context, "Successfully removed $bankName account!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error! User account not found!", Toast.LENGTH_LONG).show()
        }

        parentFragmentManager.popBackStack()
    }

    private fun showBalance() {
        accountBalanceText.text = getString(R.string.dummy_account_balance)
        showBalanceButton.text = getString(R.string.hide_balance)
        showBalanceButton.setOnClickListener { hideBalance() }
    }

    private fun hideBalance() {
        accountBalanceText.text = getString(R.string.hidden_acccount_balance)
        showBalanceButton.text = getString(R.string.show_balance)
        showBalanceButton.setOnClickListener { showBalance() }
    }

    private fun openSendMoneyPage() {
        val sendMoneyPage = SendMoneyPage()
        val args = Bundle()
        args.putString(SELECTED_BANK, bankName)
        sendMoneyPage.arguments = args

        parentFragmentManager.beginTransaction()
            .replace(R.id.navHostFragmentMainTabsPage, sendMoneyPage)
            .addToBackStack(null)
            .commit()
    }

    private fun requestCallPermission() {
        // request call permission if not granted
        val permission = Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(requireContext(), permission)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), REQUEST_CODE)
        }
    }
}