package com.luiszimermann.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
		Toast.makeText(this, "Atualizando GPS", Toast.LENGTH_SHORT).show()
	}

	fun sendImageToAPI() {
		Toast.makeText(this, "Atualizando GPS", Toast.LENGTH_SHORT).show()
	}
}