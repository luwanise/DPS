package com.banking.dps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class UserAccountsRecyclerAdapter (
    private val listener: HomePageOnItemClickListener,
    private val context: Context,
    private val userAccounts: List<UserAccount>
): RecyclerView.Adapter<UserAccountsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bankLogo: ImageView = itemView.findViewById(R.id.bankLogo)
        val bankName: TextView = itemView.findViewById(R.id.bankName)
        val userAccountsItemLayout: ConstraintLayout = itemView.findViewById(R.id.userAccountsItemLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.user_accounts_recycler_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userAccount = userAccounts[position]
        holder.bankLogo.setImageResource(userAccount.bankLogo)
        holder.bankName.text = userAccount.bankName
        holder.userAccountsItemLayout.setOnClickListener { openAccountSettings(userAccount.bankName, userAccount.id) }
    }

    private fun openAccountSettings(bankName: String, accountId: Int) {
        listener.openSelectedBankPage(bankName, accountId)
    }

    override fun getItemCount(): Int {
        return userAccounts.size
    }

}