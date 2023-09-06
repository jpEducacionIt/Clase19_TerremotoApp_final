package com.example.clase19mapsmvvm

import com.example.clase19mapsmvvm.model.data.Terremoto

fun Feature.toTerremoto() = Terremoto(
    id,
    properties.place,
    properties.mag.toString(),
    properties.time.toString(),
    geometry.longitude.toString(),
    geometry.latitude.toString()
)