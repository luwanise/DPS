package com.banking.dps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AddAccountRecyclerAdapter(
    private val listener: AddAccountOnItemClickListener,
    private val context: Context,
    private val banks: List<AddAccountBankDetails>
    ): RecyclerView.Adapter<AddAccountRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bankLogo: ImageView = itemView.findViewById(R.id.bankLogo)
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val addAccountItemLayout: ConstraintLayout = itemView.findViewById(R.id.addAccountItemLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.add_account_recycler_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = banks[position]
        holder.bankLogo.setImageResource(bank.bankLogoImageIcon)
        holder.bankName.text = bank.bankLogoName
        holder.addAccountItemLayout.setOnClickListener { listener.checkBalance(bank.bankLogoName, bank.bankLogoImageIcon, position) }
    }

    override fun getItemCount(): Int {
        return banks.size
    }
}
