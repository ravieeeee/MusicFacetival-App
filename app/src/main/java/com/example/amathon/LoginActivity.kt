package com.example.amathon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    private var user_id: EditText? = null
    private var user_password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user_id = findViewById(R.id.et_user_id)
        user_password = findViewById(R.id.et_user_password)

    }
}