package com.example.appparking.model

import com.google.gson.annotations.SerializedName

data class MovementRequest(
    @SerializedName("car_id") val cardID: Int,
    @SerializedName("enter_date") val enterDate: String
)