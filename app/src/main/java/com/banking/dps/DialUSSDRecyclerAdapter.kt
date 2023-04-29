package com.banking.dps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DialUSSDRecyclerAdapter (
    private val listener: DialUssdPageOnItemClickListener,
    private val context: Context,
    private val banks: List<AddAccountBankDetails>
): RecyclerView.Adapter<DialUSSDRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bankLogo: ImageView = itemView.findViewById(R.id.bankLogo)
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val ussdTextView: TextView = itemView.findViewById(R.id.ussdTextView)
        val dialUSSDLayout: ConstraintLayout = itemView.findViewById(R.id.dialUSSDLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.dial_ussd_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = banks[position]
        holder.bankLogo.setImageResource(bank.bankLogoImageIcon)
        holder.bankName.text = bank.bankLogoName
        holder.ussdTextView.text = BankDetailsData.bankUSSDCodes[position]

        val accountUSSD = BankDetailsData.bankUSSDCodesEncoded[position]
        holder.dialUSSDLayout.setOnClickListener { listener.runDialUssdCode(accountUSSD) }
    }

    override fun getItemCount(): Int {
        return banks.size
    }

}