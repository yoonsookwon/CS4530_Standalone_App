package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var fName: String? = null
    private var mName: String? = null
    private var lName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        fName = intent!!.getStringExtra("FirstName")
        mName = intent.getStringExtra("MiddleName")
        lName = intent.getStringExtra("LastName")

        val loginInfo = findViewById<TextView>(R.id.login_Info)
        loginInfo.text = "$fName $mName $lName is logged in!"

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}