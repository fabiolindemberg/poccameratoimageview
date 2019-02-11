package com.example.fabiolindemberg.poccameratoimageview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val MY_CAMERA_PERMISSION_CODE = 100;
    val MY_CAMERA_REQUEST_CODE = 1000;
    val MSG_ACCESS_DENIED_TO_CAMERA = "Usuário negou permissão para acessar a camera!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        This event checks if user already gives permission to app access camera
        if false the app request the permission, otherwise call startCameraActivity()
         */
        findViewById<Button>(R.id.btnGetPicture).setOnClickListener {
            if(checkSelfPermission(Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA),MY_CAMERA_PERMISSION_CODE);
            }else{
                startCameraActivity()
            }
        }
    }

    private fun startCameraActivity(){
        /*
        This create a camera intent and start it waiting for a result
         */
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*
        If the user requested for camera's permission, this method is triggered by SO
        Here we have the oportunity to check if user give permission, if true call startCameraActivity otherwise
        tells the user that the app don't have enough permission do user camera
         */
        if(requestCode == MY_CAMERA_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startCameraActivity()
            }else{
                Toast.makeText(this, MSG_ACCESS_DENIED_TO_CAMERA, Toast.LENGTH_LONG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_CAMERA_REQUEST_CODE){
            if(data != null){
                val capturedImage = data.extras.get("data") as Bitmap
                imageView.setImageBitmap(capturedImage)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }
}
