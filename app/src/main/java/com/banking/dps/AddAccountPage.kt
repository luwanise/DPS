package com.banking.dps

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hover.sdk.api.HoverParameters

class AddAccountPage : Fragment(), AddAccountOnItemClickListener {
    private lateinit var dialog: AlertDialog
    private lateinit var bankName: String
    private var bankLogo: Int? = null
    private var accountId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_account_page, container, false)

        val banksToAddRecyclerView = view.findViewById<RecyclerView>(R.id.banksToAddRecyclerView)
        val banks = BankDetailsData.loadBanks()
        Log.d(tag, "Banks: $banks")
        val adapter = AddAccountRecyclerAdapter(this, requireContext(), banks)
        banksToAddRecyclerView.adapter = adapter

        requestCallPermission()

        return view
    }

    override fun checkBalance(bank: String, logo: Int, id:Int) {
        bankName = bank
        bankLogo = logo
        accountId = id
        storeBankDetailsInDatabase()

//        when(bankName){
//            "Access Bank" -> getBalanceUsingHover("f43fc721", true)
//            "First Bank" -> getBalanceUsingHover("4226af7c", false)
//            "Guarantee Trust Bank" -> getBalanceUsingHover("37a6aa64", true)
//            "Unity Bank" -> getBalanceUsingHover("9220efdc", true)
//            "Zenith Bank" -> getBalanceUsingHover("b4a8345c", false)
//        }
    }

    private fun storeBankDetailsInDatabase(){
        val sharedPreferences = requireContext()
            .getSharedPreferences(USER_ACCOUNT_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        sharedPreferences.edit()
            .putString("$accountId", "$bankName::$bankLogo::0.0")
            .apply()

        Toast.makeText(context, "Adding $bankName to database", Toast.LENGTH_LONG).show()
        closeFragment()
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }

    private fun getBalanceUsingHover(requestId: String, requiresPassword: Boolean){
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
            closeFragment()
        }

        nextButton.setOnClickListener {
            val password = enterPasswordEditText.text.toString()
            runHoverBuilder(requiresPassword, password, requestId)
        }

        dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun runHoverBuilder(requiresPassword: Boolean, password: String, requestId: String){
        if (requiresPassword) {
            val intent = HoverParameters.Builder(requireContext().applicationContext)
                .request(requestId)
                .extra("password", password)
                .buildIntent()

            startActivity(intent)
        } else {
            val intent = HoverParameters.Builder(requireContext().applicationContext)
                .request(requestId)
                .buildIntent()

            startActivity(intent)
        }
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