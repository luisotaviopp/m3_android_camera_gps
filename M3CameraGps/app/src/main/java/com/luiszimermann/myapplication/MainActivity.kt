package com.luiszimermann.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	private val REQUEST_CODE = 42

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		btn_gps.setOnClickListener {
			updateUserLocation()
		}

		btn_camera.setOnClickListener {
			openCamera()
		}

		btn_send.setOnClickListener {
			sendImageToAPI()
		}
	}

	fun updateUserLocation() {
		Toast.makeText(this, "Atualizando GPS", Toast.LENGTH_SHORT).show()
	}

	fun openCamera() {

		val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

		// Verificando se nenhum outro app está com a câmera aberta no momento.
		if(takePictureIntent.resolveActivity(this.packageManager) != null){
			startActivityForResult(takePictureIntent, REQUEST_CODE)
		} else {
			Toast.makeText(this, "Outro APP está com a câmera aberta...", Toast.LENGTH_SHORT).show()
		}
	}

	fun sendImageToAPI() {
		Toast.makeText(this, "Atualizando GPS", Toast.LENGTH_SHORT).show()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
			val takenImage = data?.extras?.get("data") as Bitmap
			image_view.setImageBitmap(takenImage)
		} else {
			super.onActivityResult(requestCode, resultCode, data)
		}
	}
}