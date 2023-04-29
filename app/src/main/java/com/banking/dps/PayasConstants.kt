package com.banking.dps

const val USER_ACCOUNT_SHARED_PREFERENCES = "PAYAS_SHARED_PREFERENCES"
const val LOGIN_DETAILS_SHARED_PREFERENCES = "PAYAS_LOGIN_SHARED_PREFERENCES"
const val HISTORY_SHARED_PREFERENCES = "PAYAS_HISTORY_SHARED_PREFERENCES"
const val LOGIN_DETAILS = "LOGIN_DETAILS_29483"
const val TAG = "PAYAS_Logs"
const val SELECTED_BANK = "SELECTED_BANK"
const val SELECTED_BANK_ID = "SELECTED_BANK_ID"
const val REQUEST_CODE = 123

interface AddAccountOnItemClickListener {
    fun checkBalance(bank: String, logo: Int, id: Int)
}

interface HomePageOnItemClickListener {
    fun openSelectedBankPage(bankName: String, accountId: Int)
}

interface DialUssdPageOnItemClickListener {
    fun runDialUssdCode(ussdCode: String)
}