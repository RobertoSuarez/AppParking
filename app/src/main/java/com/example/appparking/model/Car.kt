package com.example.appparking.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id") val id: Int?,
    @SerializedName("plate_number") val plateNumber: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("color") val color: String?
)