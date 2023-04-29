package com.banking.dps

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class HistoryPage : Fragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history_page, container, false)

        sharedPreferences = requireContext()
            .getSharedPreferences(HISTORY_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val transactionHistory = getTransactionHistory()
        val historyRecyclerAdapter = HistoryRecyclerAdapter(requireContext(), transactionHistory)

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView)
        historyRecyclerView.adapter = historyRecyclerAdapter

        val clearTransactionHistoryButton = view.findViewById<Button>(R.id.clearTransactionHistoryButton)
        clearTransactionHistoryButton.setOnClickListener { clearTransactionHistory() }
        return view
    }

    private fun clearTransactionHistory() {
        sharedPreferences.edit().clear().apply()
        historyRecyclerView.adapter = HistoryRecyclerAdapter(requireContext(), listOf())

    }

    private fun getTransactionHistory(): List<TransactionHistory> {
        val transactions = sharedPreferences.all.values
        val transactionHistory = mutableListOf<TransactionHistory>()

        for (transaction in transactions){
            val transactionDetails = transaction.toString().split("::")
            val content = transactionDetails[0]
            val amount = transactionDetails[1]

            val transaction = TransactionHistory(content, "-N$amount")
            transactionHistory.add(transaction)
        }

        Log.d(TAG,"Transaction History Extracted: $transactionHistory")
        return transactionHistory
    }
}