package com.example.photopicker_v2

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tempImageUri: Uri? = null

        val cameraForResult =
            registerForActivityResult(
                ActivityResultContracts.TakePicture(),
                ActivityResultCallback { success ->
                    if (success)
                        photo_im.setImageURI(tempImageUri)
                }
            )

        camera_btn.setOnClickListener {
            tempImageUri = FileProvider.getUriForFile(this, "com.example.photopicker_v2.provider", createImageFile())
            cameraForResult.launch(tempImageUri)
        }

        val galleryForResult =
            registerForActivityResult(
                ActivityResultContracts.GetContent(),
                ActivityResultCallback {
                    photo_im.setImageURI(it)
                }
            )

        gallery_btn.setOnClickListener {
            galleryForResult.launch("image/*")
        }
    }

    private fun createImageFile(): File{
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) // or filesDir??
        return File.createTempFile("temp_image", ".jpg", storageDir)
    }
}