package com.banking.dps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hover.sdk.api.Hover

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Hover.initialize(this)
    }
}