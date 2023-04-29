package com.banking.dps

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class HomePage : Fragment(), HomePageOnItemClickListener {

    private lateinit var addAccountButton: Button
    private lateinit var userAccountsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        addAccountButton = view.findViewById(R.id.addAccountButton)
        addAccountButton.setOnClickListener { openAddAccountPage() }

        userAccountsRecyclerView = view.findViewById(R.id.userAccountsRecyclerView)

        val userAccounts = getUserAccounts()

        val userAccountsRecyclerAdapter = UserAccountsRecyclerAdapter(this, requireContext(), userAccounts)
        userAccountsRecyclerView.adapter = userAccountsRecyclerAdapter

        return view
    }

    private fun getUserAccounts(): List<UserAccount> {
        val sharedPreferences = requireContext()
            .getSharedPreferences(USER_ACCOUNT_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val userAccounts = sharedPreferences.all
        val userAccountList = mutableListOf<UserAccount>()

        userAccounts.forEach{ (key, value) ->
            val accountId = key.toInt()
            val userAccountDetails = value.toString().split("::")
            val bankName = userAccountDetails[0]
            val bankLogo = userAccountDetails[1].toInt()
            val accountBalance = userAccountDetails[2].toDouble()
            val userAccount = UserAccount(accountId, bankName, bankLogo, accountBalance)
            userAccountList.add(userAccount)
        }

        Log.d(TAG,"User Accounts Extracted: $userAccountList")
        return userAccountList
    }

    private fun openAddAccountPage(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.navHostFragmentMainTabsPage, AddAccountPage())
            .addToBackStack(null)
            .commit()
    }

    override fun openSelectedBankPage(bankName: String, accountId: Int) {
        val selectedBankPage = SelectedBankPage()
        val args = Bundle()
        args.putString(SELECTED_BANK, bankName)
        args.putInt(SELECTED_BANK_ID, accountId)
        selectedBankPage.arguments = args

        parentFragmentManager.beginTransaction()
            .replace(R.id.navHostFragmentMainTabsPage, selectedBankPage)
            .addToBackStack(null)
            .commit()
    }
}