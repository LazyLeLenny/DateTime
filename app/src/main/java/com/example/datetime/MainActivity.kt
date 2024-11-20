package com.example.datetime

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var setImageIV : ImageView
    private lateinit var lastNameET : EditText
    private lateinit var nameET : EditText
    private lateinit var phoneET : EditText
    private lateinit var dateET : EditText
    private lateinit var saveBTN : Button

    private var timer : CountDownTimer? = null
    val PICK_IMAGE = 23
    private var imageURI : Uri = Uri.parse("android.resource://com.example.datetime/${R.drawable.baseline_person_24}")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setImageIV = findViewById(R.id.setImageIV)
        lastNameET = findViewById(R.id.lastNameET)
        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        dateET = findViewById(R.id.dateET)
        saveBTN = findViewById(R.id.saveBTN)

        setImageIV.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        saveBTN.setOnClickListener{
            startActivity(Intent(this, CardActivity::class.java)
                .putExtra("image", imageURI.toString())
                .putExtra("lastName", lastNameET.text)
                .putExtra("name", nameET.text)
                .putExtra("phone", phoneET.text)
                .putExtra("date", dateET.text)
            )
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            setImageIV.setImageURI(imageUri)
        }
    }
}