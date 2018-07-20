package com.example.amathon

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class AfterAnalysisActivity : AppCompatActivity() {
    private var iv_selected: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        iv_selected = findViewById(R.id.iv_selected)

        val bitmap = intent.getParcelableExtra("selected") as Bitmap

        iv_selected?.setImageBitmap(bitmap)

        setContentView(R.layout.activity_afteranalysis)
    }
}