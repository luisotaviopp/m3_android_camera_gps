package com.luiszimermann.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private val REQUEST_CODE = 42
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private lateinit var locationRequest: LocationRequest
	private lateinit var locationCallback: LocationCallback

	override fun onCreate(savedInstanceState: Bundle?) {

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		updateUserLocation()

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

		// Checa as permissôes
		if (
			ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
			&& ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(this,
				arrayOf(
					android.Manifest.permission.ACCESS_FINE_LOCATION,
					android.Manifest.permission.ACCESS_COARSE_LOCATION
				),
				100
			)
		} else {
			println("Tem permissão")
		}

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
		locationRequest = LocationRequest()
		locationRequest.interval = 1000
		locationRequest.fastestInterval = 1000
		locationRequest.smallestDisplacement = 20f // 20m
		locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function

		locationCallback = object : LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult?) {
				locationResult ?: return

				if (locationResult.locations.isNotEmpty()) {

					val location = locationResult.lastLocation
					println("AAA: ${location.longitude}")

					txt_latitude.text = "Latitude:  ${location.latitude}"
					txt_longitude.text = "Longitude:  ${location.longitude}"
				}
			}
		}

		/* ----> Outro método, funciona também.

		LocationServices.getFusedLocationProviderClient(this)
			.lastLocation.addOnSuccessListener {
				if (it != null) {
					println("Longitude: ${it.longitude}")
				} else {
					println("Obj nulo")
				}
			}

		*/

	}

	// Abre a câmera.
	fun openCamera() {
		val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		startActivityForResult(takePictureIntent, REQUEST_CODE)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
			val takenImage = data?.extras?.get("data") as Bitmap
			image_view.setImageBitmap(takenImage)
		} else {
			super.onActivityResult(requestCode, resultCode, data)
		}
	}

	//
	private fun startLocationUpdates() {

		if (ActivityCompat.checkSelfPermission(
				this,
				android.Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				this,
				android.Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(
				this,
				arrayOf(
					android.Manifest.permission.ACCESS_FINE_LOCATION,
					android.Manifest.permission.ACCESS_COARSE_LOCATION
				),
				100
			)
		} else {
			println("Tem permissão")
		}

		fusedLocationClient.requestLocationUpdates(
			locationRequest,
			locationCallback,
			null
		)
	}

	// Para de coletar a localização
	private fun stopLocationUpdates() {
		fusedLocationClient.removeLocationUpdates(locationCallback)
	}

	//  Para de coletar a localização quando o app está em primeiro plano.
	override fun onPause() {
		super.onPause()
		stopLocationUpdates()
	}

	// Volta a coletar a localização quando o app está em primeiro plano.
	override fun onResume() {
		super.onResume()
		startLocationUpdates()
	}

	// Envia a imagem para a API.
	fun sendImageToAPI() {
		Toast.makeText(this, "Atualizando GPS", Toast.LENGTH_SHORT).show()
	}
}
