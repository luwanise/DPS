package com.banking.dps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerAdapter (
    private val context: Context,
    private val transactions: List<TransactionHistory>
): RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val historyContent: TextView = itemView.findViewById(R.id.historyContent)
        val historyAmount: TextView = itemView.findViewById(R.id.historyAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.history_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.historyContent.text = transaction.transactionDetails
        holder.historyAmount.text = transaction.amount
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}