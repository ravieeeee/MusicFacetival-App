package com.example.amathon

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    private var btn_select_img: Button? = null
    private var img_static: Bitmap? = null
    private var image : MultipartBody.Part?=null

    private val GALLERY_CODE = 1112

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_select_img = findViewById(R.id.btn_select_img)

        Toast.makeText(this.applicationContext, "main", Toast.LENGTH_SHORT).show()


        setContentView(R.layout.activity_main)

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
                sendHttpRequest(data)
                sendPicture(data.data)
            }
        }

        val intent2 = Intent(super.getApplicationContext(), AfterAnalysisActivity::class.java).apply {
            putExtra("selected", img_static)
        }
        startActivity(intent2)
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

        img_static = bitmap

    }

    // 표정인식!!!!
    private fun sendHttpRequest(data: Intent?) {
        val baos = ByteArrayOutputStream()

        img_static?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val photoBody = RequestBody.create(MediaType.parse("image/jpeg"),baos.toByteArray())
        val photo = File(data.toString()) // 파일의 이름을 알아내려고 한다.

        image = MultipartBody.Part.createFormData("image_profile",photo.name, photoBody)




//        val url = "52.78.25.56:3000/api/upload"
//        val obj = URL(url)
//
//        with(obj.openConnection() as HttpURLConnection) {
//            // optional default is GET
//            requestMethod = "POST"
//
//            Log.e("Post request URL", url)
////            Log.e("Response Code", responseCode)
//
////            println("\nSending 'GET' request to URL : $url")
////            println("Response Code : $responseCode")
//
//            BufferedReader(InputStreamReader(inputStream)).use {
//                val response = StringBuffer()
//
//                var inputLine = it.readLine()
//                while (inputLine != null) {
//                    response.append(inputLine)
//                    inputLine = it.readLine()
//                }
//                println(response.toString())
//            }
//        }
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
}
