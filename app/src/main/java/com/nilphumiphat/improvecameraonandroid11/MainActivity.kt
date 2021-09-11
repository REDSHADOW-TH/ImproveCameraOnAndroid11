package com.nilphumiphat.improvecameraonandroid11

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.nilphumiphat.improvecameraonandroid11.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val cameraReqId: Int = 55555
    private val storageReqId = 5555555

    private var bitmapData: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.viewModel = MainViewModel()

        binding.btnOpenCamera.setOnClickListener {
            openCameraProcess()
        }
    }

    private fun isPermissionGrant(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun permissionGrant(
        activity: Activity = this,
        permissionList: Array<String>,
        requestId: Int
    ) {
        ActivityCompat.requestPermissions(
            activity, permissionList, requestId
        )
    }

    private fun openCameraProcess() {
        if (!isPermissionGrant(applicationContext, Manifest.permission.CAMERA)) {
            permissionGrant(this, arrayOf(Manifest.permission.CAMERA), cameraReqId)
            openCameraProcess()
            return
        }
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraReqId)
    }

    private fun saveFileProcess() {
        bitmapData?.saveImage(applicationContext)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraReqId) {
            bitmapData = (data?.extras?.get("data") as Bitmap)
            if (bitmapData != null) binding.imgView.setImageBitmap(bitmapData)
            saveFileProcess()
        }
    }


}