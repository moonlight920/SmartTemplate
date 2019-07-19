package com.yf.smarttemplate.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.yf.smarttemplate.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


val DEBUG = true
internal inline fun <reified T : Any> T.debugLog(value: String) {
    if (DEBUG) {
        Log.d("SmartTemplate", this::class.java.simpleName + value)
    }
}
