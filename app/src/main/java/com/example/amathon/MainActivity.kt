package com.example.amathon

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView


import java.io.IOException
import android.R.attr.data
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.app.NotificationCompat.getExtras
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var btn_select_img: Button? = null
    private var img_static: Bitmap? = null
    private var iv_selected: ImageView? = null

    private val GALLERY_CODE = 1112

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_select_img = findViewById(R.id.btn_select_img)

        Toast.makeText(this.applicationContext, "main", Toast.LENGTH_SHORT).show()


        setContentView(R.layout.activity_main)

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("onRestart!!!!", "!!!!")
//        setContentView(R.layout.activity_afteranalysis)
        Toast.makeText(this.applicationContext, "restart!!!!", Toast.LENGTH_SHORT).show()
//
//        iv_selected = findViewById(R.id.iv_selected)
//        iv_selected?.setImageBitmap(img_static)


    }

    fun onClick_btn_select_img(v: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_CODE && data != null) {
                Log.e("data : ", data.toString())
                Log.e("data : ", data.extras.toString())

                if (data.data == null) {
                    Log.e("data check", "is Null")
                } else {
                    Log.e("data check", "not Null")
                }

                sendPicture(data.data)
            }
        }

        val intent2 = Intent(super.getApplicationContext(), AfterAnalysisActivity::class.java).apply {
            putExtra("selected", img_static)
        }
        startActivity(intent2)
        Toast.makeText(this.applicationContext, "after intent", Toast.LENGTH_SHORT).show()
//        finish()
    }

    private fun sendPicture(imgUri: Uri?) {
        val imagePath = getRealPathFromURI(imgUri) // path 경로
        var exif: ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val exifOrientation = exif!!.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val exifDegree = exifOrientationToDegrees(exifOrientation)

        val bitmap = BitmapFactory.decodeFile(imagePath)//경로를 통해 비트맵으로 전환

        Log.e("bitmap Test", bitmap.toString() + "")
        if (bitmap == null) {
            Log.e("bitmap Test", "bitmap is null!!!")
        }
        Log.e("bitmap!!", rotate(bitmap, exifDegree.toFloat()).toString())
        Log.e("bitmap!!", rotate(bitmap, exifDegree.toFloat()).toString())
//        iv_selected!!.setImageBitmap(rotate(bitmap, exifDegree.toFloat()))//이미지 뷰에 비트맵 넣기
//        iv_selected!!.setImageBitmap(bitmap)//이미지 뷰에 비트맵 넣기
//        iv_selected?.setImageBitmap(bitmap)
        Log.e("success", "~~~")

        img_static = bitmap
    }

    // 사진 절대경로 가져오기
    private fun getRealPathFromURI(contentUri: Uri?): String {
        var column_index = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(column_index)
    }

    // 사진 회전값 가져오기
    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    // 정방향으로 회전
    private fun rotate(src: Bitmap, degree: Float): Bitmap {
        // Matrix 객체 생성
        val matrix = Matrix()

        // 회전 각도 셋팅
        matrix.postRotate(degree)

        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.width,
                src.height, matrix, true)
    }
}
