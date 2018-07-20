package com.example.amathon

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

class AfterAnalysisActivity : AppCompatActivity() {
    private var iv_selected: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afteranalysis)

        Toast.makeText(this.applicationContext, "after", Toast.LENGTH_SHORT).show()

        iv_selected = findViewById(R.id.iv_selected)
        val bitmap = intent.getParcelableExtra("selected") as Bitmap
        Log.e("11111", bitmap.toString())
        iv_selected?.setImageBitmap(bitmap)

    }

}