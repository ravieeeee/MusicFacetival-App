package com.example.amathon

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class AfterAnalysisActivity : AppCompatActivity() {
    private var iv_selected: ImageView? = null
    lateinit var tv_result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afteranalysis)

        iv_selected = findViewById(R.id.iv_selected)
        tv_result = findViewById(R.id.tv_result)

        val bitmap = intent.getParcelableExtra("selected") as Bitmap
        val recog_result = intent.getStringExtra("recog_result")

        tv_result.setText(recog_result)
        iv_selected?.setImageBitmap(bitmap)
    }
}