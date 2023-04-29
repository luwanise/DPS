package com.banking.dps

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DialUssdPage : Fragment(), DialUssdPageOnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dial_ussd_page, container, false)

        val dialUssdRecyclerView = view.findViewById<RecyclerView>(R.id.dialUssdRecyclerView)
        dialUssdRecyclerView.adapter = DialUSSDRecyclerAdapter(this, requireContext(), BankDetailsData.loadBanks())

        return view
    }

    override fun runDialUssdCode(ussdCode: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$ussdCode")
        }
        startActivity(intent)
    }
}