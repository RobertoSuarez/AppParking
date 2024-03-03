package com.example.appparking.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movement(
    @SerializedName("car_id") val carID: Int?,
    @SerializedName("movement_id") val movementID: Int?,
    @SerializedName("plate_number") val plateNumber: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("color") val color: String?,
    @SerializedName("enter_date") val enterDate: String?
) : Serializable

