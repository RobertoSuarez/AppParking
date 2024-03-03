package com.example.appparking.model

import com.google.gson.annotations.SerializedName

class MovementUpdate (
    @SerializedName("id") val id: Int,
    @SerializedName("amount") val amount: Float,
    @SerializedName("exit_date") val exitDate: String
)