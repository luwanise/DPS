package com.banking.dps

import android.net.Uri

object BankDetailsData {
    private val hashTag = Uri.encode("#")
    val bankUSSDCodes = listOf("*901#", "*894#", "*737#", "*7799#", "*966#")
    val bankUSSDCodesEncoded = listOf("*901$hashTag", "*894$hashTag", "*737$hashTag", "*7799$hashTag", "*966$hashTag")

    fun loadBanks(): MutableList<AddAccountBankDetails> {
        val banks = mutableListOf<AddAccountBankDetails>()
        val bankNames = listOf("Access Bank", "First Bank", "Guarantee Trust Bank", "Unity Bank", "Zenith Bank")
        val bankLogos = listOf(R.drawable.access, R.drawable.firstbank, R.drawable.gtbank, R.drawable.unity, R.drawable.zenith)

        for (i in bankNames.indices){
            val bankDetails = AddAccountBankDetails(bankLogos[i], bankNames[i])
            banks.add(bankDetails)
        }

        return banks
    }
}