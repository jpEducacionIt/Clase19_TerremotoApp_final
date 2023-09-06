package com.example.clase19mapsmvvm.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clase19mapsmvvm.R
import com.example.clase19mapsmvvm.model.data.Terremoto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    var latitud = 0.0
    var longitud = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val bundle = intent.extras
        val terremoto: Terremoto? = bundle?.getParcelable("terremoto")
        latitud = terremoto?.lat?.toDouble()!!
        longitud = terremoto.longitud.toDouble()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(latitud, longitud)
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Marker")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8F))
    }
}